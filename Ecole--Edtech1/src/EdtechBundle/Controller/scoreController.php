<?php

namespace EdtechBundle\Controller;

use EdtechBundle\Entity\score;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;

/**
 * Score controller.
 *
 * @Route("score")
 */
class scoreController extends Controller
{
    /**
     * Lists all score entities.
     *
     * @Route("/", name="score_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $scores = $em->getRepository('EdtechBundle:score')->findAll();

        return $this->render('score/index.html.twig', array(
            'scores' => $scores,
        ));
    }

    /**
     * Creates a new score entity.
     *
     * @Route("/new", name="score_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $score = new Score();
        $form = $this->createForm('EdtechBundle\Form\scoreType', $score);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($score);
            $em->flush();

            return $this->redirectToRoute('score_show', array('id' => $score->getId()));
        }

        return $this->render('score/new.html.twig', array(
            'score' => $score,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a score entity.
     *
     * @Route("/{id}", name="score_show")
     * @Method("GET")
     */
    public function showAction(score $score)
    {
        $deleteForm = $this->createDeleteForm($score);

        return $this->render('score/show.html.twig', array(
            'score' => $score,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing score entity.
     *
     * @Route("/{id}/edit", name="score_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, score $score)
    {
        $deleteForm = $this->createDeleteForm($score);
        $editForm = $this->createForm('EdtechBundle\Form\scoreType', $score);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('score_edit', array('id' => $score->getId()));
        }

        return $this->render('score/edit.html.twig', array(
            'score' => $score,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a score entity.
     *
     * @Route("/{id}", name="score_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, score $score)
    {
        $form = $this->createDeleteForm($score);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($score);
            $em->flush();
        }

        return $this->redirectToRoute('score_index');
    }

    /**
     * Creates a form to delete a score entity.
     *
     * @param score $score The score entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(score $score)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('score_delete', array('id' => $score->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
