<?php

namespace ClubBundle\Controller;

use ClubBundle\Entity\Activity;
use ClubBundle\Form\ActivityType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class ActivityController extends Controller
{
    public function getUserr()
    {
        $user = null;
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
        }
        return $user;
    }
    public function updateActivityAction($id, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $club = $em->getRepository(Activity::class)->find($id);
        $form = $this->createForm(ActivityType::class, $club);
        $form = $form->handleRequest($request);
        if ($form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('addActivity');
        }
        return $this->render('@Club/Activity/update.html.twig', array(
            'form' => $form->createView()
        ));
    }

    public function deleteActivityAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $rs = $em->getRepository(Activity::class)->find($id);
        $em->remove($rs);
        $em->flush();
        return $this->redirectToRoute('addActivity');
    }

    public function addActivityAction(Request $request)
    {

        //$users = $em->getRepository(Club::class)->FindENS();
        $em = $this->getDoctrine()->getManager();
        $Activityall = $em->getRepository("ClubBundle:Activity")->findAll();


        $Activity = new Activity();
        $form = $this->createForm(ActivityType::class, $Activity);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em->persist($Activity);
            $em->flush();
            return $this->redirectToRoute("addActivity");
        }
        return $this->render('@Club/Activity/add.html.twig', array(
            'form' => $form->createView(), 'Activity' => $Activityall
        ));
    }

    public function front_view_activityAction(Request $request)
    {
        $r = $this->getDoctrine()->getRepository(Activity::class)->findAll();
        return $this->render('@Club/Activity/front/activity_view.html.twig', array("Activity" => $r,"USER" => $this->getUserr()));
    }




    public function front_vote_activity_plusAction($idA)
    {
        $em = $this->getDoctrine()->getManager();

        $r = $this->getDoctrine()->getRepository(Activity::class)->find($idA);
        $r->setVote($r->getVote()+1);
        $r->addUsersVote($this->getUserr());
        $em->flush();


        return $this->redirectToRoute("FrontaddActivity");
    }


    public function front_vote_activity_minusAction($idA)
    {
        $em = $this->getDoctrine()->getManager();
        $r = $this->getDoctrine()->getRepository(Activity::class)->find($idA);
        $r->setVote($r->getVote()-1);
        $r->removeUsersVote($this->getUserr());
        $em->flush();

        return $this->redirectToRoute("FrontaddActivity");
    }

    public function tri_activityAction()
    {
        $Activity= $this->getDoctrine()->getRepository(Activity::class)->findAllOrderedByRate();
        return $this->render('@Club/Activity/front/activity_view.html.twig', array("Activity" => $Activity,"USER" => $this->getUserr()));

    }

}
