<?php

namespace EnseignantBundle\Controller;

use EnseignantBundle\Entity\Sanctions;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

/**
 * Sanction controller.
 *
 */
class SanctionsController extends Controller
{
    /**
     * Lists all sanction entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $sanctions = $em->getRepository('EnseignantBundle:Sanctions')->findAll();

        return $this->render('sanctions/index.html.twig', array(
            'sanctions' => $sanctions,
        ));
    }

    /**
     * Creates a new sanction entity.
     *
     */
    public function newAction(Request $request)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $sanction = new Sanctions();
        $sanction->setEnseignant($user);
        $sanction->setEtat(false);
        $form = $this->createForm('EnseignantBundle\Form\SanctionsType', $sanction, ['classe' => $classe]);
        $form->handleRequest($request);

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $sanction->setEnseignant($user);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($sanction);
            $em->flush();

            return $this->redirectToRoute('sanctions_show', array('id' => $sanction->getId()));
        }

        return $this->render('sanctions/new.html.twig', array(
            'sanction' => $sanction,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a sanction entity.
     *
     */
    public function showAction(Sanctions $sanction)
    {
        $deleteForm = $this->createDeleteForm($sanction);

        return $this->render('sanctions/show.html.twig', array(
            'sanction' => $sanction,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing sanction entity.
     *
     */
    public function editAction(Request $request, Sanctions $sanction)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $classe=$user->getClasse();
        $deleteForm = $this->createDeleteForm($sanction);
        $editForm = $this->createForm('EnseignantBundle\Form\SanctionsType', $sanction, ['classe' => $classe]);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('sanctions_index');
        }

        return $this->render('sanctions/edit.html.twig', array(
            'sanction' => $sanction,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    public function changeretatAction(Sanctions $sanction)
    {
        $etat=$sanction->isEtat();
        $em = $this->getDoctrine()->getManager();
        if($etat==false) {$sanction->setEtat(true);}
        else
        { $sanction->setEtat(false);}
        $em->persist($sanction);
        $em->flush();
        return $this->redirectToRoute('sanctions_index');
    }

    /**
     * Deletes a sanction entity.
     *
     */
    public function deleteAction(Sanctions $sanction)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($sanction);
        $entityManager->flush();
        return $this->redirectToRoute('sanctions_index');
    }

    /**
     * Creates a form to delete a sanction entity.
     *
     * @param Sanctions $sanction The sanction entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Sanctions $sanction)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('sanctions_delete', array('id' => $sanction->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
