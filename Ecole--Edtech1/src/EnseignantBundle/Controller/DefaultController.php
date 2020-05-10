<?php

namespace EnseignantBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class DefaultController extends Controller
{
    public function indexAction(Request $request)
    {
        return $this->render('default/index.html.twig');
    }

    public function backAction(Request $request)
    {
        return $this->render('back.html.twig');
    }

    public function listeAction(Request $request)
    {
        return $this->render('listedeseleves.html.twig');
    }
}
