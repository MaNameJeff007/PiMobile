<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Matiere;
use EnseignantBundle\Entity\Notes;
use EnseignantBundle\Entity\Moyennes;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Matiere controller.
 *
 * @Route("matiere")
 */
class MatiereController extends Controller
{

    public function newMatiereApiAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $cl = new Matiere();
        $cl->setNom($request->get('nom'));
        $cl->setNbH($request->get('nbH'));
        $em->persist($cl);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($cl);
        return new JsonResponse($formatted);
    }

    public function editMatiereApiAction(Request $request,$id)
    {
        $cl=$this->getDoctrine()->getRepository(Matiere::class)->find($id);

        $en=$this->getDoctrine()->getManager();
        $cl->setNom($request->get('nom'));
        $cl->setNbH($request->get('nbH'));
        $en->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('moyennes','notes'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $serializer = new Serializer($normalizer, $encoder);
        $formatted = $serializer->serialize($cl, 'json');
        $formatted1 = $serializer->normalize($cl);
        return new JsonResponse($formatted1);
    }

    public function supprimerMatiereApiAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Matiere::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('moyennes','notes'));
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($c, 'json');
        $formatted1 = $serializer->normalize($c);
        return new JsonResponse($formatted1);

    }

    public function allMatAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Matiere')
            ->findAll();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('moyennes','notes'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted =$serializer->serialize($tasks, 'json');
        $formatted1 = $serializer->normalize($tasks);
        return new JsonResponse($formatted1);
    }

    public function FindMatAction($name)
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Matiere')
            ->findMatFilter($name);
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('moyennes','notes'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted =$serializer->serialize($tasks, 'json');
        $formatted1 = $serializer->normalize($tasks);
        return new JsonResponse($formatted1);
    }

    /**
     * Lists all matiere entities.
     *
     * @Route("/", name="matiere_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $matieres = $em->getRepository('ScolariteBundle:Matiere')->findAll();

        return $this->render('matiere/index.html.twig', array(
            'matieres' => $matieres,
        ));
    }

    /**
     * Creates a new matiere entity.
     *
     * @Route("/new", name="matiere_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $matiere = new Matiere();
        $form = $this->createForm('ScolariteBundle\Form\MatiereType', $matiere);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $cn = $matiere->getNom();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Matiere::class);
            $check = $em1->findMat($cn);

            if (!count($check)) {

                $em = $this->getDoctrine()->getManager();
                $em->persist($matiere);
                $em->flush();

                return $this->redirectToRoute('matiere_index', array('id' => $matiere->getId()));
            } else {
                $this->addFlash(
                    'errorC ', 'Cette matiere  existe deja !.'
                );
                return $this->render('default/errors.html.twig');

            }
        }

        return $this->render('matiere/new.html.twig', array(
            'matiere' => $matiere,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a matiere entity.
     *
     * @Route("/{id}", name="matiere_show")
     * @Method("GET")
     */
    public function showAction(Matiere $matiere)
    {
        $deleteForm = $this->createDeleteForm($matiere);

        return $this->render('matiere/show.html.twig', array(
            'matiere' => $matiere,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing matiere entity.
     *
     * @Route("/{id}/edit", name="matiere_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Matiere $matiere)
    {
        $deleteForm = $this->createDeleteForm($matiere);
        $editForm = $this->createForm('ScolariteBundle\Form\MatiereType', $matiere);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {

            $cn = $matiere->getNom();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Matiere::class);
            $check = $em1->findMat($cn);

            if (!count($check)) {

                $this->getDoctrine()->getManager()->flush();

                return $this->redirectToRoute('matiere_edit', array('id' => $matiere->getId()));
            } else {
                $this->addFlash(
                    'errorC ', 'Cette matiere  existe deja !.'
                );
                return $this->render('default/errors.html.twig');

            }
        }


        return $this->render('matiere/edit.html.twig', array(
            'matiere' => $matiere,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a matiere entity.
     *
     * @Route("/{id}", name="matiere_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Matiere $matiere)
    {
        $form = $this->createDeleteForm($matiere);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($matiere);
            $em->flush();
        }

        return $this->redirectToRoute('matiere_index');
    }

    /**
     * Creates a form to delete a matiere entity.
     *
     * @param Matiere $matiere The matiere entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Matiere $matiere)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('matiere_delete', array('id' => $matiere->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    public function supprimerMAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Matiere::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('matiere_index');
    }

    public function allMAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Matiere')
            ->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tasks);
        return new JsonResponse($formatted);
    }
}
