<?php

namespace GestionBundle\Controller;

use GestionBundle\Entity\Attestation;
use GestionBundle\Entity\Permutation;
use GestionBundle\Entity\Reclamation;
use ScolariteBundle\Entity\Classe;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\User;

/**
 * Permutation controller.
 *
 * @Route("permutation")
 */
class PermutationController extends Controller
{
    /**
     * Lists all permutation entities.
     *
     * @Route("/", name="permutation_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $permutations = $em->getRepository('GestionBundle:Permutation')->findBy(array('parent' => $user->getId()));

        return $this->render('permutation/index.html.twig', array(
            'permutations' => $permutations,
        ));
    }


    public function newAction(Request $request,$parent)
    {
        $permutation = new Permutation();
        $permutation->setDate(new \DateTime('now'));
        $permutation->setEtat("non traitee");
        $em = $this->getDoctrine()->getManager();
        $p=$em->getRepository(\AppBundle\Entity\User::class)->find($parent);
        $permutation->setParent($p);

        $enfant=$em->getRepository(\AppBundle\Entity\User::class)->findBy(array("parent"=>$parent));

        $en=$em->getRepository(\AppBundle\Entity\User::class)->findOneBy(array("parent"=>$parent));
       // $permutation->$enfant->getClasse();
        $permutation->setEleve($en);
      //  $user = $this->container->get('security.token_storage')->getToken()->getUser();

        $form = $this->createForm('GestionBundle\Form\PermutationType', $permutation);
        $form->handleRequest($request);




        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($permutation);
            $em->flush();

            return $this->redirectToRoute('permutationindex', array('id' => $permutation->getId()));
        }

        return $this->render('permutation/new.html.twig', array(
            'permutation' => $enfant,
            'form' => $form->createView(),
        ));
    }
    public function searchpermutationajaxAction(Request $request,$valeur)
    {

        $em=$this->getDoctrine()->getManager();
        $valeur=trim($valeur);
        $ssearch=$em->getRepository('GestionBundle:Permutation')->PermutationSearchajax($valeur);

        if($ssearch)
        {
            $resultats=array();

            foreach ($ssearch as $s)
            {
                $resultats[$s->getId()]["id"]=$s->getId();
                $resultats[$s->getId()]["classeS"]=$s->getClasseS();
                $resultats[$s->getId()]["raison"]=$s->getRaison();
                $resultats[$s->getId()]["etat"]=$s->getEtat();
            }
        }
        else
        {
            $resultats=null;
        }

        $response=new JsonResponse();
        return $response->setData(array('valeur'=>$resultats));

    }

    /**
     * Finds and displays a permutation entity.
     *
     * @Route("/{id}", name="permutation_show")
     * @Method("GET")
     */
    public function showAction(Permutation $permutation)
    {
        return $this->render('permutation/show.html.twig', array(
            'permutation' => $permutation,

        ));
    }

    /**
     * Displays a form to edit an existing permutation entity.
     *
     * @Route("/{id}/edit", name="permutation_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Permutation $permutation)
    {
        $deleteForm = $this->createDeleteForm($permutation);
        $editForm = $this->createForm('GestionBundle\Form\PermutationType', $permutation);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('permutation_index', array('id' => $permutation->getId()));
        }

        return $this->render('permutation/edit.html.twig', array(
            'permutation' => $permutation,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a permutation entity.
     *
     * @Route("/{id}", name="permutation_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Permutation $permutation)
    {
        $form = $this->createDeleteForm($permutation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($permutation);
            $em->flush();
        }

        return $this->redirectToRoute('permutation_index');
    }
    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Permutation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('permutationindex');
    }

    public function indexadminAction()
    {
        $em = $this->getDoctrine()->getManager();

        $permutations = $em->getRepository('GestionBundle:Permutation')->findAll();

        return $this->render('permutation/adminconsulter.html.twig', array(
            'permutations' => $permutations,
        ));
    }

    public function adminsupprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Permutation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('permutationadmin');
    }

    public function traiterAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Permutation::class)->find($id);
        $user=$this->getDoctrine()->getRepository(\AppBundle\Entity\User::class)->find($c->getParent());
        $en=$this->getDoctrine()->getManager();
        $c->setEtat("traitee");
        $en->persist($c);
        $en->flush();

        $username='topisland123@gmail.com';
        $message= \Swift_Message::newInstance()
            ->setSubject('Permutation')
            ->setFrom($username)
            ->setTo($user->getEmail())
            ->setBody('Votre demande de permutation a été traitée');
        $this->get('mailer')->send($message);

        return $this->redirectToRoute('permutationadmin');
    }

    /**
     * Creates a form to delete a permutation entity.
     *
     * @param Permutation $permutation The permutation entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Permutation $permutation)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('permutation_delete', array('id' => $permutation->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
