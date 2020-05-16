<?php

namespace EnseignantBundle\Controller;

use EnseignantBundle\Entity\Moyennes;
use EnseignantBundle\Entity\Notes;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Moyenne controller.
 *
 */
class MoyennesController extends Controller
{
    /**
     * Lists all moyenne entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $moyennes = $em->getRepository('EnseignantBundle:Moyennes')->findAll();

        return $this->render('moyennes/index.html.twig', array(
            'moyennes' => $moyennes,
        ));
    }

    /**
     * Creates a new moyenne entity.
     *
     */
    public function newAction(Request $request, $ideleve, $idmatiere, $trimestre)
    {
        $em = $this->getDoctrine()->getManager();
        $eleve = $em->getRepository('AppBundle:User')->find($ideleve);
        $matiere = $em->getRepository('ScolariteBundle:Matiere')->find($idmatiere);
        $moyenne = new moyennes();
        $moyenne->setTrimestre($trimestre);
        $moyenne->setMatiere($matiere);
        $moyenne->setEleve($eleve);

        $repository = $this->getDoctrine()->getRepository(Notes::class);
        $query = $repository->createQueryBuilder('n')
            ->where('n.eleve=:ideleve')->andWhere('n.matiere=:idmatiere')->andWhere('n.id_trimestre=:trimestre')
            ->setParameter('ideleve', $ideleve)
            ->setParameter('idmatiere', $idmatiere)
            ->setParameter('trimestre', $trimestre)
            ->getQuery();
        $notes = $query->getResult();

        if ($notes != NULL) {
            $size = count($notes);
            $valeurmoyenne = 0;
            $tab = [];

            for ($i = 0; $i < $size; $i++) {
                $tab[$i] = $notes[$i];
            }

            for ($i = 0; $i < $size; $i++) {
                if ($tab[$i]->getType() == "CC") {
                    $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.2);
                } elseif ($tab[$i]->getType() == "Devoir de controle") {
                    $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.3);
                } elseif ($tab[$i]->getType() == "Devoir de synthese") {
                    $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.5);
                }
            }

            $r = $this->getDoctrine()->getRepository(Moyennes::class);
            $query = $r->createQueryBuilder('n')
                                ->where('n.eleve=:ideleve')->andWhere('n.matiere=:idmatiere')->andWhere('n.trimestre=:trimestre')
                                ->setParameter('ideleve', $ideleve)
                                ->setParameter('idmatiere', $idmatiere)
                                ->setParameter('trimestre', $trimestre)
                                ->getQuery();


            $moyennes = $query->getResult();

            if($moyennes!=NULL)
            {
                return $this->redirectToRoute('notes_index');
            }

            else {
                $moyenne->setMoyenne($valeurmoyenne);
                $em->persist($moyenne);
                $em->flush();
                return $this->redirectToRoute('moyennes_index');
            }
        }
    }
    /**
     * Finds and displays a moyenne entity.
     *
     */
    public function showAction(moyennes $moyenne)
    {
        $deleteForm = $this->createDeleteForm($moyenne);

        return $this->render('moyennes/show.html.twig', array(
            'moyenne' => $moyenne,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing moyenne entity.
     *
     */
    public function editAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $moyenne = $em->getRepository('EnseignantBundle:Moyennes')->find($id);
        if($moyenne==NULL)
            return $this->redirectToRoute('notes_index');

        $repository = $this->getDoctrine()->getRepository(Notes::class);
        $query = $repository->createQueryBuilder('n')
            ->where('n.eleve=:ideleve')->andWhere('n.matiere=:idmatiere')->andWhere('n.id_trimestre=:trimestre')
            ->setParameter('ideleve', $moyenne->getEleve())
            ->setParameter('idmatiere', $moyenne->getMatiere())
            ->setParameter('trimestre', $moyenne->getTrimestre())
            ->getQuery();
        $notes = $query->getResult();

        $size = count($notes);

        $valeurmoyenne = 0;
        $tab = [];

        for ($i = 0; $i < $size; $i++) {
            $tab[$i] = $notes[$i];
        }

        for ($i = 0; $i < $size; $i++) {
            if ($tab[$i]->getType() == "CC")
            {
                $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.2);
            }
            elseif ($tab[$i]->getType() == "Devoir de controle")
            {
                $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.3);
            }
            elseif ($tab[$i]->getType() == "Devoir de synthese")
            {
                $valeurmoyenne = $valeurmoyenne + ($tab[$i]->getValeur() * 0.5);
            }
        }

        $moyenne->setMoyenne($valeurmoyenne);
        $em->flush();
        return $this->redirectToRoute('moyennes_index');
    }

    /**
     * Deletes a moyenne entity.
     *
     */
    public function deleteAction(moyennes $moyenne)
    {

        $em = $this->getDoctrine()->getManager();
        $em->remove($moyenne);
        $em->flush();

        return $this->redirectToRoute('moyennes_index');
    }

    /**
     * Creates a form to delete a moyenne entity.
     *
     * @param Moyennes $moyenne The moyenne entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Moyennes $moyenne)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('moyennes_delete', array('id' => $moyenne->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }


    public function ajouterAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $moy = new Moyennes();

        $eleve = $this->getDoctrine()->getManager()->getRepository('AppBundle:User')->findOneById($request->get('eleve'));
        $matiere = $this->getDoctrine()->getManager()->getRepository('EnseignantBundle:Matiere')->findOneById($request->get('matiere'));

        $moy->setTrimestre($request->get('trimestre'));
        $moy->setMatiere($matiere);
        $moy->setEleve($eleve);
        $moy->setMoyenne($request->get('moyenne'));

        $em->persist($moy);
        $em->flush();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setIgnoredAttributes(array('eleve', 'matiere'));

        $normalizers[0]->setCircularReferenceHandler(function ($object)
        {
            return $object->getId();
        });

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($moy);
        return new JsonResponse($formatted);
    }

    public function modifierAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $moy=$em->getRepository('EnseignantBundle:Moyennes')->findOneById($request->get('id'));
        $moy->setMoyenne($request->get('moyenne'));
        $em->flush();
        return new JsonResponse("modification reussie.");
    }

    public function verifierAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT * FROM moyennes WHERE eleve_id=? AND matiere=? AND trimestre=?";
        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(3, $request->get('trimestre'));
        $statement->bindValue(1, $request->get('eleve'));
        $statement->bindValue(2, $request->get('matiere'));
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

    public function allAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT moyennes.id, moyennes.trimestre, moyennes.eleve_id, moyennes.matiere, user.prenom, user.nom as eleve_nom, matiere.nom as matiere_nom, moyennes.moyenne FROM `moyennes` INNER JOIN user ON moyennes.eleve_id=user.id INNER JOIN matiere ON moyennes.matiere=matiere.id WHERE user.classeeleve_id=? ORDER BY eleve_id ASC";

        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('classeenseignant'));
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

    public function moyenneseleveAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT moyennes.id, moyennes.trimestre, moyennes.eleve_id, moyennes.matiere, user.prenom, user.nom as eleve_nom, matiere.nom as matiere_nom, moyennes.moyenne FROM `moyennes` INNER JOIN user ON moyennes.eleve_id=user.id INNER JOIN matiere ON moyennes.matiere=matiere.id WHERE user.id=? ORDER BY eleve_id ASC";

        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('id'));
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
        $moy = $entityManager->getRepository('EnseignantBundle:Moyennes')->findOneById($request->get('id'));
        $entityManager->remove($moy);
        $entityManager->flush();
        return new JsonResponse("suppression reussie.");
    }

    public function moyennestatsAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT moyennes.id, moyennes.trimestre, moyennes.eleve_id, moyennes.matiere, user.prenom, user.nom as eleve_nom, matiere.nom as matiere_nom, moyennes.moyenne FROM `moyennes` INNER JOIN user ON moyennes.eleve_id=user.id INNER JOIN matiere ON moyennes.matiere=matiere.id WHERE eleve_id =? AND matiere =? ORDER BY moyennes.trimestre ASC";;

        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('eleve'));
        $statement->bindValue(2, $request->get('matiere'));
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

    public function getMatiereStatsMoyennesAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $sql="SELECT matiere.id,  matiere.nom, moyennes.moyenne FROM `moyennes` INNER JOIN user ON moyennes.eleve_id=user.id INNER JOIN matiere ON moyennes.matiere=matiere.id WHERE eleve_id =? GROUP BY matiere.id";

        $statement = $em->getConnection()->prepare($sql);
        $statement->bindValue(1, $request->get('eleve'));
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
