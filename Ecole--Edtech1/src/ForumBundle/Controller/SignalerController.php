<?php

namespace ForumBundle\Controller;

use ForumBundle\Entity\Commentaire;
use ForumBundle\Entity\Signaler;
use ForumBundle\Entity\Sujet;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;

/**
 * Signaler controller.
 *
 * @Route("signaler")
 */
class SignalerController extends Controller
{
    /**
     * Lists all signaler entities.
     *
     * @Route("/", name="signaler_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $signalers = $em->getRepository('ForumBundle:Signaler')->findAll();

        return $this->render('signaler/index.html.twig', array(
            'signalers' => $signalers,
        ));
    }

    /**
     * Creates a new signaler entity.
     *
     * @Route("/new", name="signaler_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request ,Sujet $s)
    {
        $em = $this->getDoctrine()->getManager();
        $ss = $em->getRepository(Signaler::class)->findOneBy(array("sujet"=>$s));
        if ($ss==null)
        {
            $signaler = new Signaler();
            $signaler->setSujet($s);
            $signaler->setType("sujet");
            $em->persist($signaler);
            $em->flush();
        }
        else{
            $ss->setNombre($ss->getNombre()+1);
            $em->persist($ss);
            $em->flush();
        }
        return $this->redirectToRoute('sujet_index');
    }

    public function newcommAction(Request $request ,Commentaire $s)
    {
        $em = $this->getDoctrine()->getManager();
        $ss = $em->getRepository(Signaler::class)->findOneBy(array("commentaire"=>$s));
        if ($ss==null)
        {
            $signaler = new Signaler();
            $signaler->setCommentaire($s);
            $signaler->setType("commentaire");
            $em->persist($signaler);
            $em->flush();
        }
        else{
            $ss->setNombre($ss->getNombre()+1);
            $em->persist($ss);
            $em->flush();
        }
        $id = $this->get('session')->get('id');
        return $this->redirectToRoute('sujet_show',["id"=>$id]);
    }

    /**
     * Finds and displays a signaler entity.
     *
     * @Route("/{id}", name="signaler_show")
     * @Method("GET")
     */
    public function showAction(Signaler $signaler)
    {
        $deleteForm = $this->createDeleteForm($signaler);

        return $this->render('signaler/show.html.twig', array(
            'signaler' => $signaler,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing signaler entity.
     *
     * @Route("/{id}/edit", name="signaler_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Signaler $signaler)
    {
        $deleteForm = $this->createDeleteForm($signaler);
        $editForm = $this->createForm('ForumBundle\Form\SignalerType', $signaler);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('signaler_edit', array('id' => $signaler->getId()));
        }

        return $this->render('signaler/edit.html.twig', array(
            'signaler' => $signaler,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a signaler entity.
     *
     * @Route("/{id}", name="signaler_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Signaler $signaler)
    {
        $form = $this->createDeleteForm($signaler);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($signaler);
            $em->flush();
        }

        return $this->redirectToRoute('signaler_index');
    }

    public function supprimerAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Signaler::class)->find($id);
        $en->remove($aa);
        $en->flush();
        return $this->redirectToRoute('signaler_index');
    }

    public function supprimertousAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aaa = $en->getRepository(Signaler::class)->find($id);
        if($aaa->getSujet()!=null)
        {
        $aa = $en->getRepository(Sujet::class)->find($aaa->getSujet());
        $dza=$en->getRepository(Commentaire::class)->findbyfa($aa);
        $s=$this->getDoctrine()->getRepository(Sujet::class)->find($id);
        $en->remove($aa);
        }
        else
        {
            $dza=$en->getRepository(Commentaire::class)->find($aaa->getCommentaire());
            $en->remove($dza);
        }

        $en->remove($aaa);
        $en->flush();
        return $this->redirectToRoute('signaler_index');
    }

    /**
     * Creates a form to delete a signaler entity.
     *
     * @param Signaler $signaler The signaler entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Signaler $signaler)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('signaler_delete', array('id' => $signaler->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
