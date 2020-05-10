<?php

namespace GestionBundle\Controller;

use EnseignantBundle\Entity\Notes;
use GestionBundle\Entity\Attestation;
use GestionBundle\Entity\Reclamation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\User;

/**
 * Reclamation controller.
 *
 * @Route("reclamation")
 */
class ReclamationController extends Controller
{
    /**
     * Lists all reclamation entities.
     *
     * @Route("/", name="reclamation_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $reclamations = $em->getRepository('GestionBundle:Reclamation')->findBy(array('parent' => $user->getId()));

        return $this->render('reclamation/index.html.twig', array(
            'reclamations' => $reclamations,
        ));
    }
    public function indexadminAction()
    {
        $em = $this->getDoctrine()->getManager();

        $reclamations = $em->getRepository('GestionBundle:Reclamation')->findAll();

        return $this->render('reclamation/adminconsulter.html.twig', array(
            'reclamations' => $reclamations,
        ));
    }

    public function addreclamationAction(Request $request,$note,$parent) {
        $r = new Reclamation();
        $r->setDate(new \DateTime('now'));
        $r->setEtat("non traitee");

        $em = $this->getDoctrine()->getManager();
        $p=$em->getRepository(\AppBundle\Entity\User::class)->find($parent);
        $r->setParent($p);

        $n=$em->getRepository(Notes::class)->find($note);
        $r->setNote($n);
        if($r->getParent()!=null) {
            $em->persist($r);
            $em->flush();
        }
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $reclamations = $em->getRepository('GestionBundle:Reclamation')->findBy(array('parent' => $user->getId()));

        return $this->render('reclamation/index.html.twig', array(
            'reclamations' => $reclamations));
    }

    /**
     * Creates a new reclamation entity.
     *
     * @Route("/new", name="reclamation_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $reclamation = new Reclamation();
        $reclamation->setDate(new \DateTime('now'));
        $reclamation->setEtat("non traitee");
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $reclamation->setIduser($user->getId());
        $form = $this->createForm('GestionBundle\Form\ReclamationType', $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();

            return $this->redirectToRoute('reclamation_show', array('id' => $reclamation->getId()));
        }

        return $this->render('reclamation/new.html.twig', array(
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a reclamation entity.
     *
     * @Route("/{id}", name="reclamation_show")
     * @Method("GET")
     */
    public function showAction(Reclamation $reclamation)
    {

        return $this->render('reclamation/show.html.twig', array(
            'reclamation' => $reclamation
        ));
    }

    /**
     * Displays a form to edit an existing reclamation entity.
     *
     * @Route("/{id}/edit", name="reclamation_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Reclamation $reclamation)
    {
        $deleteForm = $this->createDeleteForm($reclamation);
        $editForm = $this->createForm('GestionBundle\Form\ReclamationType', $reclamation);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('reclamation_index', array('id' => $reclamation->getId()));
        }

        return $this->render('reclamation/edit.html.twig', array(
            'reclamation' => $reclamation,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Reclamation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('reclamationindex');
    }




    public function adminsupprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Reclamation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('reclamationadmin');
    }

    public function traiterAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Reclamation::class)->find($id);
        $user=$this->getDoctrine()->getRepository(\AppBundle\Entity\User::class)->find($c->getParent());
        $en=$this->getDoctrine()->getManager();
        $c->setEtat("traitee");
        $en->persist($c);
        $en->flush();

        $username='topisland123@gmail.com';
        $message= \Swift_Message::newInstance()
            ->setSubject('Réclamation')
            ->setFrom($username)
            ->setTo($user->getEmail())
            ->setBody('Votre réclamation a été traitée');
        $this->get('mailer')->send($message);

        return $this->redirectToRoute('reclamationadmin');
    }

    /**
     * Creates a form to delete a reclamation entity.
     *
     * @param Reclamation $reclamation The reclamation entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Reclamation $reclamation)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('reclamation_delete', array('id' => $reclamation->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
