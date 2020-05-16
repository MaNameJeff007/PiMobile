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

        public function allAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT sanctions.id, sanctions.enseignant_id, sanctions.eleve_id, sanctions.date_sanction, sanctions.raisonsanction, sanctions.etat, sanctions.punition, user.prenom, user.nom FROM `sanctions` INNER JOIN user ON user.id=sanctions.eleve_id WHERE enseignant_id=? ORDER BY sanctions.eleve_id ASC";
        $statement = $em->getConnection()->prepare($sql);

        // Set parameters
        $statement->bindValue(1, $id);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceHandler(function ($object)
        {
            return $object->getId();
        });

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);

        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }


    public function sanctionseleveAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT sanctions.id, sanctions.enseignant_id, sanctions.eleve_id, sanctions.date_sanction, sanctions.raisonsanction, sanctions.etat, sanctions.punition, user.prenom, user.nom FROM `sanctions` INNER JOIN user ON user.id=sanctions.eleve_id WHERE eleve_id=? ORDER BY sanctions.punition ASC";
        $statement = $em->getConnection()->prepare($sql);

        // Set parameters
        $statement->bindValue(1, $id);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceHandler(function ($object)
        {
            return $object->getId();
        });

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);

        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }

    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sanction=new Sanctions();

        $enseignant = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('enseignant'));
        $eleve = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('eleve'));

        $sanction->setEnseignant($enseignant);
        $sanction->setEleve($eleve);
        $sanction->setEtat($request->get('etat'));

        $literalTime    =   \DateTime::createFromFormat("Y-m-d", $request->get('date'));
        $sanction->setDateSanction($literalTime);
        $sanction->setPunition($request->get('punition'));
        $sanction->setRaisonsanction($request->get('raison'));

        $em->persist($sanction);
        $em->flush();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setIgnoredAttributes(array('enseignant', 'eleve'));

        $normalizers[0]->setCircularReferenceHandler(function ($object)
        {
            return $object->getId();
        });

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($sanction);
        return new JsonResponse($formatted);
    }

    public function modifierAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sanction=$em->getRepository('EnseignantBundle:Sanctions')->findOneById($request->get('id'));
        $sanction->setPunition($request->get('punition'));
        $sanction->setRaisonsanction($request->get('justification'));
        $em->flush();
        return new JsonResponse("modification reussie.");
    }

    public function supprimerAction(Request $request)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $sanction = $entityManager->getRepository('EnseignantBundle:Sanctions')->findOneById($request->get('id'));
        $entityManager->remove($sanction);
        $entityManager->flush();
        return new JsonResponse("suppression reussie.");
    }
}
