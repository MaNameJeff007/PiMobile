<?php

namespace ClubBundle\Controller;

use ClubBundle\Entity\Activity;
use ClubBundle\Entity\Evenement;
use ClubBundle\Form\EvenementType;
use Knp\Component\Pager\Paginator;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;


class EvenementController extends Controller
{
    public function getUserr()
    {
        $user = null;
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
        }
        return $user;
    }

    public function addEventAction(Request $request)
    {
        //$users = $em->getRepository(Club::class)->FindENS();
        $em = $this->getDoctrine()->getManager();
        $evenementx = $em->getRepository("ClubBundle:Evenement")->findAll();

        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $evenement->uploadProfilePicture();
            $em->persist($evenement);
            $em->flush();
            return $this->redirectToRoute("addEvent");
        }
        return $this->render('@Club/Evenement/add.html.twig', array(
            'form' => $form->createView(), 'Evenement' => $evenementx
        ));
    }

    public function afficherEventMobileAction($idc)
    {
        $Evenement=$this->getDoctrine()->getRepository(Evenement::class)->findAllByidClub($idc);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($Evenement);
        return new JsonResponse($formatted);
    }

    public function viewAction()
    {
        return $this->render('@Club/Evenement/view.html.twig', array(// ...
        ));
    }

    public function updateAction($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $club = $em->getRepository(Evenement::class)->find($id);

        $form = $this->createForm(EvenementType::class, $club);
        $form = $form->handleRequest($request);
        if ($form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('addEvent');
        }
        return $this->render('@Club/Evenement/update.html.twig', array(
            'form' => $form->createView()
        ));
    }

    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $rs = $em->getRepository(Evenement::class)->find($id);
        $em->remove($rs);
        $em->flush();
        return $this->redirectToRoute('addEvent');
    }

    public function searchAction()
    {
        return $this->render('@Club/Evenement/search.html.twig', array(// ...
        ));
    }

    public function inscrit_eventAction(Request $request, $idE)
    {
        $em = $this->getDoctrine()->getManager();

        $user = $this->getUserr();
        $eve = $em->getRepository(Evenement::class)->find($idE);

        $eve->addEvent($user);

        $em->flush();
        return $this->redirectToRoute('front_view_event');
    }


    public function front_view_eventAction(Request $request)
    {

        $r = $this->getDoctrine()->getRepository(Evenement::class)->findAll();


        return $this->render('@Club/Evenement/front/event_view.html.twig', array(
            "Evenement" => $r,
            "USER" => $this->getUserr()
        ));
    }

    public function front_event_membersAction($idE)
    {
        $r = $this->getDoctrine()->getRepository(Evenement::class)->find($idE);

        return $this->render('@Club/Evenement/front/event_members.html.twig',array("Evenement"=>$r,"USER" => $this->getUserr()));

    }

}
