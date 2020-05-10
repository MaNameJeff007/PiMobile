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
}
