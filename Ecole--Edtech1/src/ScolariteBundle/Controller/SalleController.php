<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Salle;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Salle controller.
 *
 * @Route("salle")
 */
class SalleController extends Controller
{
    public function allSAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Salle')
            ->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tasks);
        return new JsonResponse($formatted);
    }

    public function moyAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Moyennesgenerales')
            ->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($tasks);
        return new JsonResponse($formatted);
    }

    public function supprimerSalleApiAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Salle::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);

    }

    public function editSalleApiAction(Request $request,$id)
    {
        $c=$this->getDoctrine()->getRepository(Salle::class)->find($id);

        $en=$this->getDoctrine()->getManager();
        $c->setLibelle($request->get('libelle'));
        $en->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);

    }

    public function ajouterSalleApiAction(Request $request)
    {
        $en=$this->getDoctrine()->getManager();
        $cl = new Salle();
        $cl->setLibelle($request->get('libelle'));
        $en->persist($cl);
        $en->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($cl);
        return new JsonResponse($formatted);
    }



    /**
     * Lists all salle entities.
     *
     * @Route("/", name="salle_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $salles = $em->getRepository('ScolariteBundle:Salle')->findAll();

        return $this->render('salle/index.html.twig', array(
            'salles' => $salles,
        ));
    }

    /**
     * Creates a new salle entity.
     *
     * @Route("/new", name="salle_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $salle = new Salle();
        $form = $this->createForm('ScolariteBundle\Form\SalleType', $salle);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $s=$salle->getLibelle();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Salle::class);
            $check = $em1->findSalle($s);

            if (!count($check)) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($salle);
            $em->flush();

            return $this->redirectToRoute('salle_index', array('id' => $salle->getId()));
        }else
            {
                $this->addFlash(
                    'errorC ', 'Cette salle existe déja  !'
                );
                return $this->render('default/errors.html.twig');
            }
        }

        return $this->render('salle/new.html.twig', array(
            'salle' => $salle,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a salle entity.
     *
     * @Route("/{id}", name="salle_show")
     * @Method("GET")
     */
    public function showAction(Salle $salle)
    {
        $deleteForm = $this->createDeleteForm($salle);

        return $this->render('salle/show.html.twig', array(
            'salle' => $salle,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing salle entity.
     *
     * @Route("/{id}/edit", name="salle_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Salle $salle)
    {
        $deleteForm = $this->createDeleteForm($salle);
        $editForm = $this->createForm('ScolariteBundle\Form\SalleType', $salle);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {

            $s=$salle->getLibelle();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Salle::class);
            $check = $em1->findSalle($s);

            if (!count($check)) {
                $this->getDoctrine()->getManager()->flush();

                return $this->redirectToRoute('salle_edit', array('id' => $salle->getId()));
            }else
            {
                $this->addFlash(
                    'errorC ', 'Cette salle existe déja  !'
                );
                return $this->render('default/errors.html.twig');
            }
        }


        return $this->render('salle/edit.html.twig', array(
            'salle' => $salle,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a salle entity.
     *
     * @Route("/{id}", name="salle_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Salle $salle)
    {
        $form = $this->createDeleteForm($salle);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($salle);
            $em->flush();
        }

        return $this->redirectToRoute('salle_index');
    }

    /**
     * Creates a form to delete a salle entity.
     *
     * @param Salle $salle The salle entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Salle $salle)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('salle_delete', array('id' => $salle->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    public function supprimerSalleAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Salle::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('salle_index');
    }



}
