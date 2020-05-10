<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Notification;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
//use SBC\NotificationsBundle\Builder\NotificationBuilder;
//use SBC\NotificationsBundle\Model\NotifiableInterface;
class NotificationController extends Controller
{
    public function DisplayAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $ens= $user->getId();
        $em=$this->getDoctrine()->getManager()->getRepository(Notification::class);
        $notifications=$em->GetNotEn($ens);
           // ->findAll();
        return $this->render('user/indexenseignant.html.twig',array('notifications'=>$notifications));
    }

    public function CountAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $ens= $user->getId();
        $em=$this->getDoctrine()->getManager()->getRepository(Notification::class);
        $notifications=$em->CountNotEn($ens);
        // ->findAll();
        return $this->render('seance/notifications.html.twig',array('nb'=>$notifications));
    }

    public function MarkAsSeenAction($id)
    {
        $notification=$this->getDoctrine()->getManager()->getRepository(Notification::class)
            ->findBy($id);
        $notification -> setSeen(true);
        $notifications=$this->getDoctrine()->getManager()->getRepository(Notification::class)
            ->findAll();
        return $this->render('seance/notifications.html.twig',array('notifications'=>$notifications));
    }

    public function ResetAction($id)
    {
      $c=$this->getDoctrine()->getRepository(Notification::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('enseignant_homepage');
        //return $this->render('@App/Security/enseignant_home.html.twig');
    }
}
