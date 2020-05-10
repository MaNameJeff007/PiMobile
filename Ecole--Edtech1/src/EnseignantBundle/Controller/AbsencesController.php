<?php

namespace EnseignantBundle\Controller;

use EnseignantBundle\Entity\Absences;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

/**
 * Absence controller.
 *
 */
class AbsencesController extends Controller
{
    /**
     * Lists all absence entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $absences = $em->getRepository('EnseignantBundle:Absences')->findAll();

        return $this->render('absences/index.html.twig', array(
            'absences' => $absences,
        ));
    }

    /**
     * Creates a new absence entity.
     *
     */
    public function newAction(Request $request)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $absence = new Absences();
        $absence->setEnseignant($user);
        $absence->setEtat(false);
        $absence->setJustification("a ajouter");
        $form = $this->createForm('EnseignantBundle\Form\AbsencesType', $absence, ['classe' => $classe]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($absence);
            $em->flush();

            return $this->redirectToRoute('absences_show', array('id' => $absence->getId()));
        }

        return $this->render('absences/new.html.twig', array(
            'absence' => $absence,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a absence entity.
     *
     */
    public function showAction(Absences $absence)
    {
        $deleteForm = $this->createDeleteForm($absence);

        return $this->render('absences/show.html.twig', array(
            'absence' => $absence,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing absence entity.
     *
     */
    public function editAction(Request $request, Absences $absence)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $deleteForm = $this->createDeleteForm($absence);
        $editForm = $this->createForm('EnseignantBundle\Form\AbsencesType', $absence, ['classe' => $classe]);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('absences_index');
        }

        return $this->render('absences/edit.html.twig', array(
            'absence' => $absence,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a absence entity.
     *
     */
    public function deleteAction(Absences $absence)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($absence);
        $entityManager->flush();
        return $this->redirectToRoute('absences_index');
    }

    public function changeretatAction(Absences $absence)
    {
        $etat=$absence->isEtat();
        $em = $this->getDoctrine()->getManager();
        if($etat==false) {$absence->setEtat(true);}
        else
        { $absence->setEtat(false);}
        $em->persist($absence);
        $em->flush();
        return $this->redirectToRoute('absences_index');
    }


    /**
     * Creates a form to delete a absence entity.
     *
     * @param Absences $absence The absence entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Absences $absence)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('absences_delete', array('id' => $absence->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
