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

    
    public function allAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $sql = "SELECT absences.id, absences.dateabs, absences.justification, absences.heuredebut, absences.heurefin, absences.etat, absences.enseignant_id, absences.eleve_id, user.prenom, user.nom FROM `absences` INNER JOIN user ON absences.eleve_id=user.id WHERE enseignant_id=? ORDER BY absences.eleve_id ASC";
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


    public function absenceseleveAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $sql = "SELECT absences.id, absences.dateabs, absences.justification, absences.heuredebut, absences.heurefin, absences.etat, absences.enseignant_id, absences.eleve_id, user.prenom, user.nom FROM `absences` INNER JOIN user ON absences.eleve_id=user.id WHERE eleve_id=?";
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
        $absence=new Absences();

        $enseignant = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('enseignant'));
        $eleve = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('eleve'));

        $absence->setEnseignant($enseignant);
        $absence->setEleve($eleve);
        $absence->setJustification($request->get('justification'));
        $absence->setEtat($request->get('etat'));
        $absence->setHeuredebut($request->get('heuredebut'));
        $absence->setHeurefin($request->get('heurefin'));

        $literalTime    =   \DateTime::createFromFormat("Y-m-d", $request->get('date'));
        $absence->setDateAbs($literalTime);

        $em->persist($absence);
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
        $formatted = $serializer->normalize($absence);
        return new JsonResponse($formatted);
    }

    public function modifierAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $abs=$em->getRepository('EnseignantBundle:Absences')->findOneById($request->get('id'));
        $bool=filter_var($request->get('etat'), FILTER_VALIDATE_BOOLEAN);
        $abs->setEtat($bool);
        $abs->setJustification($request->get('justification'));
        $em->flush();
        return new JsonResponse("modification reussie.");
    }

    public function supprimerAction(Request $request)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $abs = $entityManager->getRepository('EnseignantBundle:Absences')->findOneById($request->get('id'));
        $entityManager->remove($abs);
        $entityManager->flush();
        return new JsonResponse("suppression reussie.");
    }

    public function verifierAction(Request $request)
    {
        $sql="SELECT * FROM absences WHERE heuredebut=? AND heurefin=? and dateabs=? AND eleve_id=?";
        $em = $this->getDoctrine()->getManager();
        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $request->get('heuredebut'));
        $statement->bindValue(2, $request->get('heurefin'));
        $statement->bindValue(3, $request->get('date'));
        $statement->bindValue(4, $request->get('eleve'));
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
}
