<?php

namespace ScolariteBundle\Controller;

use EnseignantBundle\EnseignantBundle;
use EnseignantBundle\Entity\Bulletin;
use EnseignantBundle\Entity\Notes;
use ScolariteBundle\Entity\Coeff;
use EnseignantBundle\Repository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
/**
 * Coeff controller.
 *
 * @Route("coeff")
 */
class CoeffController extends Controller
{
    /**
     * Lists all coeff entities.
     *
     * @Route("/", name="coeff_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $coeffs = $em->getRepository('ScolariteBundle:Coeff')->findAll();

        return $this->render('coeff/index.html.twig', array(
            'coeffs' => $coeffs,
        ));
    }

    /**
     * Creates a new coeff entity.
     *
     * @Route("/new", name="coeff_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $coeff = new Coeff();
        $form = $this->createForm('ScolariteBundle\Form\CoeffType', $coeff);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $cn = $coeff->getNiveau();
            $cf=$coeff->getValeur();
            $cc=$coeff->getMatiere();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
            $check = $em1->findCoeff($cn,$cc);

            if (!count($check)) {



            $em = $this->getDoctrine()->getManager();
            $em->persist($coeff);
            $em->flush();

            return $this->redirectToRoute('coeff_index', array('id' => $coeff->getId()));
        }else{
                $this->addFlash(
                    'errorC ', 'Les classe du meme niveau doivent avoir le meme coefficient pour toute les matières !'
                );
                return $this->render('default/errors.html.twig');
            }
        }

        return $this->render('coeff/new.html.twig', array(
            'coeff' => $coeff,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a coeff entity.
     *
     * @Route("/{id}", name="coeff_show")
     * @Method("GET")
     */
    public function showAction(Coeff $coeff)
    {
        $deleteForm = $this->createDeleteForm($coeff);

        return $this->render('coeff/show.html.twig', array(
            'coeff' => $coeff,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing coeff entity.
     *
     * @Route("/{id}/edit", name="coeff_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Coeff $coeff)
    {
        $deleteForm = $this->createDeleteForm($coeff);
        $editForm = $this->createForm('ScolariteBundle\Form\CoeffType', $coeff);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {

            $cn = $coeff->getNiveau();
            $cf=$coeff->getValeur();
            $cc=$coeff->getMatiere();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
            $check = $em1->findCoeff($cn,$cc);

            if (!count($check)) {



                $this->getDoctrine()->getManager()->flush();


                return $this->redirectToRoute('coeff_edit', array('id' => $coeff->getId()));
            }else{
                $this->addFlash(
                    'errorC ', 'Les classe du meme niveau doivent avoir le meme coefficient pour toute les matières !'
                );
                return $this->render('default/errors.html.twig');
            }
        }


        return $this->render('coeff/edit.html.twig', array(
            'coeff' => $coeff,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a coeff entity.
     *
     * @Route("/{id}", name="coeff_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Coeff $coeff)
    {
        $form = $this->createDeleteForm($coeff);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($coeff);
            $em->flush();
        }

        return $this->redirectToRoute('coeff_index');
    }

    /**
     * Creates a form to delete a coeff entity.
     *
     * @param Coeff $coeff The coeff entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Coeff $coeff)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('coeff_delete', array('id' => $coeff->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
    public function supprimerCAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Coeff::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('coeff_index');
    }

    public function CAction(Request $request,$eleve,$trimestre,$classeE)
    {
        $moyenne = new Bulletin();
        $moyenne->setEleve($eleve);
        $moyenne->setClasse($classeE);
        $moyenne->setTrimestre($trimestre);
        $em = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
        $results = $em->MEleve($classeE, $trimestre);
        //$results = $em->REleve1($eleve, $tr);

        foreach ($results as $row) {
                $res=$row['moyG'];
                //$p=$em->REleve2($res,$tr);
            $moyenne->setMoyenne($res);
                //$m = $row['moyG'];

            }
        $em = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
        $ch = $em-> TrouverB($eleve,$trimestre);
        if (!count($ch))
        {
            $em = $this->getDoctrine()->getManager();
            $em->persist($moyenne);

            $em->flush();
        }

//        return $this->redirectToRoute('classe_index');

        $em=$this->getDoctrine()->getManager()->getRepository(Coeff::class);

        $hh=$em->TrouverMatiere($trimestre,$eleve);
        $n=$em-> TrouverNotes($trimestre,$eleve);
        $e=$em->TrouverE($eleve);

        //$html = $this->renderView('bulletin/bulletin.html.twig',array('fr'=>$n,'b'=>$ch));
        $html = $this->renderView('bulletin/bulletin.html.twig',array('fr'=>$n,'b'=>$ch,'hh'=>$hh,'e'=>$e));
        //$html = $this->renderView('default/bulletin.html.twig');

        $filename = sprintf('test-%s.pdf', date('Y-m-d'));

        return new Response(
            $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
            200,
            [
                'Content-Type'        => 'application/pdf',
                'Content-Disposition' => sprintf('inline; filename="%s"', $filename),
            ]
        );




    }


}
