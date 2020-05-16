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


    
    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $note = new Notes();

        $enseignant = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('enseignant'));
        $eleve = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('eleve'));
        $matiere = $this->getDoctrine()->getManager()->getRepository('EnseignantBundle:Matiere')->findOneById($request->get('matiere'));

        $note->setIdTrimestre($request->get('trimestre'));
        $note->setType($request->get('type'));
        $note->setMatiere($matiere);
        $note->setEleve($eleve);
        $note->setEnseignant($enseignant);
        $note->setValeur($request->get('valeur'));


        $em->persist($note);
        $em->flush();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setIgnoredAttributes(array('enseignant', 'eleve', 'matiere'));

        $normalizers[0]->setCircularReferenceHandler(function ($object)
        {
            return $object->getId();
        });

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($note);
        return new JsonResponse($formatted);
    }

    public function allAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT notes.id, notes.type, notes.id_trimestre, notes.enseignant_id, notes.eleve_id, notes.matiere, notes.valeur, user.prenom as eleve_prenom, user.nom as eleve_nom, matiere.nom as matiere_nom FROM `notes` INNER JOIN matiere ON notes.matiere=matiere.id INNER JOIN user ON notes.eleve_id=user.id WHERE notes.enseignant_id=? ORDER BY notes.id ASC";

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

    public function noteseleveAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT notes.id, notes.type, notes.id_trimestre, notes.enseignant_id, notes.eleve_id, notes.matiere, notes.valeur, user.prenom as eleve_prenom, user.nom as eleve_nom, matiere.nom as matiere_nom FROM `notes` INNER JOIN matiere ON notes.matiere=matiere.id INNER JOIN user ON notes.eleve_id=user.id WHERE notes.eleve_id=? ORDER BY notes.type ASC";

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

    public function supprimerAction(Request $request)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $note = $entityManager->getRepository('EnseignantBundle:Notes')->findOneById($request->get('id'));
        $entityManager->remove($note);
        $entityManager->flush();
        return new JsonResponse("suppression reussie.");
    }

    public function getMatieresAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT id, nom FROM matiere";
        $statement = $em->getConnection()->prepare($sql);
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

    public function verifiernoteAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT * FROM notes WHERE id_trimestre = ?  AND eleve_id = ? AND matiere = ? AND type = ?";
        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('trimestre'));
        $statement->bindValue(2, $request->get('eleve'));
        $statement->bindValue(3, $request->get('matiere'));
        $statement->bindValue(4, $request->get('type'));
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

    public function modifierNoteAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $note=$em->getRepository('EnseignantBundle:Notes')->findOneById($request->get('id'));
        $note->setType($request->get('type'));
        $note->setValeur($request->get('valeur'));
        $em->flush();
        return new JsonResponse("modification reussie.");
    }

    public function getnotespourmoyenneAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT * FROM notes WHERE id_trimestre =? AND eleve_id =? AND matiere =?";
        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('trimestre'));
        $statement->bindValue(2, $request->get('eleve'));
        $statement->bindValue(3, $request->get('matiere'));
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

    public function mailnoteAction(Request $Request)
    {
        $note=new Notes();

        $eleve = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($Request->get('eleve'));
        $matiere = $this->getDoctrine()->getManager()->getRepository('EnseignantBundle:Matiere')->findOneById($Request->get('matiere'));
        $parent=$eleve->getParent();
        $email=$parent->getEmail();
        $note->setIdTrimestre($Request->get('trimestre'));
        $note->setMatiere($matiere);
        $note->setType($Request->get('type'));
        $note->setEleve($eleve);
        $sujet="Ajout d'une note.";
        $message = (new \Swift_Message($sujet))
            ->setFrom('mohamedaminejrad1@gmail.com')
            ->setTo($email)
            ->setBody(
                $this->renderView(
                // app/Resources/views/Emails/registration.html.twig
                    'mailnote.html.twig',
                    ['note' => $note]
                ),
                'text/html'
            );



        $this->get('mailer')->send($message);

        return new JsonResponse("envoie reussie");
    }

    public function notestatsAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT * FROM notes WHERE type =? AND eleve_id =? AND matiere =? ORDER BY id_trimestre";
        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('type'));
        $statement->bindValue(2, $request->get('eleve'));
        $statement->bindValue(3, $request->get('matiere'));
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
