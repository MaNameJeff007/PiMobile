<?php

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
class SecurityController extends Controller
{

    public function getTokenAction()
    {
        return new Response($this->container->get('form.csrf_provider')
            ->generateCsrfToken('authenticate'));
    }

    /**
     * @Route("/add")
     */
    public function addAction()
    {
        return $this->render('AppBundle:Security:add.html.twig', array(
            // ...
        ));
    }

    /**
     * @Route("/home")
     */
    public function redirectAction()
    {
        $authCheker =$this->container->get('security.authorization_checker');
        if($authCheker->isGranted('ROLE_ADMIN')){return $this->render('http://127.0.0.1:8000/adminhomepage');}
        else if ($authCheker->isGranted('ROLE_PARENT'))
        {return $this->render('@App/Security/parent_home.html.twig');}
        else if ($authCheker->isGranted('ROLE_ENSEIGNANT'))
        {return $this->render('@App/Security/enseignant_home.html.twig');}

        else {return $this->render('@FOSUser/Security/login.html.twig');}

    }

}
