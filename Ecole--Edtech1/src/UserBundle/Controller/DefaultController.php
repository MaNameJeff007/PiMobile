<?php

namespace UserBundle\Controller;

use AppBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('UserBundle:Default:index.html.twig');
    }

    public function loginAction($username)
    {
        $em = $this->getDoctrine()->getManager();


        $req="SELECT * FROM `user` WHERE username=?";
        $statement = $em->getConnection()->prepare($req);

        // Set parameters
        $statement->bindValue(1, $username);
        $statement->execute();
        $result = $statement->fetchAll();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }    
}
