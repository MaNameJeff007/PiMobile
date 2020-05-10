<?php

namespace EnseignantBundle\Controller;

use EnseignantBundle\Entity\Notes;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use UserBundle\Entity\User;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
/**
 * Note controller.
 *
 */
class NotesController extends Controller
{
    /**
     * Lists all note entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $notes = $em->getRepository('EnseignantBundle:Notes')->findAll();

        return $this->render('notes/index.html.twig', array(
            'notes' => $notes,
        ));
    }

    /**
     * Creates a new note entity.
     *
     */
    public function newAction(Request $request)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $note = new Notes();
        $note->setEnseignant($user);

        $form = $this->createForm('EnseignantBundle\Form\NotesType', $note, ['classe' => $classe]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($note);
            $em->flush();

            return $this->redirectToRoute('notes_index', array('id' => $note->getId()));
        }

        return $this->render('notes/new.html.twig', array(
            'note' => $note,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a note entity.
     *
     */
    public function showAction(Notes $note)
    {
        $deleteForm = $this->createDeleteForm($note);

        return $this->render('notes/show.html.twig', array(
            'note' => $note,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing note entity.
     *
     */
    public function editAction(Request $request, Notes $note)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $deleteForm = $this->createDeleteForm($note);
        $editForm = $this->createForm('EnseignantBundle\Form\NotesType', $note, ['classe' => $classe]);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('notes_index');
        }

        return $this->render('notes/edit.html.twig', array(
            'note' => $note,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a note entity.
     *
     */
    public function deleteAction(Notes $note)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($note);
        $entityManager->flush();
        return $this->redirectToRoute('notes_index');
    }

    /**
     * Creates a form to delete a note entity.
     *
     * @param Notes $note The note entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Notes $note)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('notes_delete', array('id' => $note->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
