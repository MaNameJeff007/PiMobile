<?php

namespace ClubBundle\Controller;

use AppBundle\Entity\User;
use ClubBundle\Entity\Club;
use ClubBundle\Form\ClubType;
use Knp\Component\Pager\Paginator;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class ClubController extends Controller
{

    public function getUserr()
    {
        $user = null;
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
        }
        return $user;
    }
    public function setunseennotifAction()
    {
        $em = $this->getDoctrine()->getManager();
        $notificationNotSeen= $em->getRepository('ClubBundle:Notification')->findnotseen();

        foreach ($notificationNotSeen as $nns)
        {
            $nns->setSeen(1);
            $em->persist($nns);

        }
        $em->flush();

        return $this->redirectToRoute('front_home_page');
    }
    public function setoneunseennotifAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $notificationNotSeen= $em->getRepository('ClubBundle:Notification')->find($id);
        $notificationNotSeen->setSeen(1);
        $em->persist($notificationNotSeen);
        $em->flush();

        return $this->redirectToRoute('front_home_page');
    }
    public function displayAction()
    {
        $em = $this->getDoctrine()->getManager();

        $notificationNotSeen= $em->getRepository('ClubBundle:Notification')->findnotseen();
        $not=count($notificationNotSeen);

        $notification= $em->getRepository('ClubBundle:Notification')
            ->searchAll();

        return($this->render('@Club/Default/notif.html.twig',array(
            'notifications' => $notification,
            'notifnb'=> $not

        )));
    }
    public function addClubAction(Request $request)
    {
        //$em = $this->getDoctrine()->getManager();
        //$users = $em->getRepository(Club::class)->FindENS();
        $em = $this->getDoctrine()->getManager();
        $clubx = $em->getRepository("ClubBundle:Club")->findAll();


        /**
         * @var $paginator Paginator
         */

        $paginator = $this->get('knp_paginator');

        dump(get_class($paginator));
        $re=$paginator->paginate(
            $clubx,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit',2));


        $club = new Club();
        $form = $this->createForm(ClubType::class, $club);
        $form->handleRequest($request);
        //  $club->setSujet(null);
        if ($form->isSubmitted()) {
            $club->uploadProfilePicture();
            $em->persist($club);
            $em->flush();
            return $this->redirectToRoute("add");
        }
        return $this->render('@Club/Club/add.html.twig', array(
            'form' => $form->createView(),
            //'users'=>$users,
            'userconecter' => $this->getUserr(),
            "club" => $re
        ));
    }
    public function front_view_clubsAction(Request $request)
    {
        $r=$this->getDoctrine()->getRepository(Club::class)->findAll();
        /**
         * @var $paginator Paginator
         */
        $paginator = $this->get('knp_paginator');

        dump(get_class($paginator));
        $re=$paginator->paginate(
            $r,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit',2));
        return $this->render('@Club/Club/front/club_view.html.twig', array("clubs"=>$re));
    }



    public function searchclubajaxAction(Request $request,$valeur)
    {

        $em=$this->getDoctrine()->getManager();
        $valeur=trim($valeur);
        $ssearch=$em->getRepository('ClubBundle:Club')->ClubSearchajax($valeur);

        if($ssearch)
        {
            $resultats=array();

            foreach ($ssearch as $s)
            {
                $resultats[$s->getId()]["id"]=$s->getId();
                $resultats[$s->getId()]["nomClub"]=$s->getNomclub();
                $resultats[$s->getId()]["nomUser"]=$s->getUser()->getNom();
                $resultats[$s->getId()]["nomImage"]=$s->getNomImage();
            }
        }
        else
        {
            $resultats=null;
        }

        $response=new JsonResponse();
        return $response->setData(array('valeur'=>$resultats));

    }
    public function front_home_pageAction()
    {
        return $this->render('Front/front.base.html.twig', array(
            // ...
        ));
    }
    /*public function front_home_pageAction()
    {
        return $this->render('@Club/Club/front/home_page.html.twig', array(
            // ...
        ));
    }*/
    /* public function viewAction()
     {
         $club = new Club();
         $form = $this->createForm(ClubType::class, $club);

         $em = $this->getDoctrine()->getManager();
         $clubx = $em->getRepository("ClubBundle:Club")->findAll();
         return $this->render('@Club/Club/add.html.twig', array(
             "club" => $clubx,
             'form' => $form->createView(),
             'userconecter'=>$this->getUserr(),
         ));
     }*/
    public function afficherclubAction(Request $request)
    {

        $em = $this->getDoctrine()->getManager();
       // $club = $em->getRepository("ClubBundle:Club")->findAll();
        $dql   = "SELECT a FROM ClubBundle:Club a";
        $query = $em->createQuery($dql);

        $paginator = $this->get('knp_paginator');
        $result = $paginator->paginate(
            $query,
            $request->query->getInt('page', 0),
            $request->query->getInt('limit',2)
);

        return $this->render('?', array("club" => $club));
    }
    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $rs = $em->getRepository(Club::class)->find($id);
        $em->remove($rs);
        $em->flush();
        return $this->redirectToRoute('add');
    }
    public function updateAction($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $club = $em->getRepository(club::class)->find($id);
        $form = $this->createForm(clubType::class, $club);
        $form = $form->handleRequest($request);
        if ($form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('add');
        }
        return $this->render('@Club/Club/update.html.twig', array(
            'form' => $form->createView()
        ));
    }
    public function afficherclubMobileAction()
    {
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $club=$this->getDoctrine()->getRepository(Club::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $normalizers = array($normalizer);
        $serializer2=new Serializer($normalizers);
        $formatted = $serializer2->normalize($club);
        return new JsonResponse($formatted);
    }








}
