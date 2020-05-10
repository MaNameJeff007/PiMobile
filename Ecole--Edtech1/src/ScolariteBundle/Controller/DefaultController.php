<?php

namespace ScolariteBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('ScolariteBundle:Default:index.html.twig');
    }
}
