<?php

namespace GestionBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\JsonResponse;
class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('GestionBundle:Default:index.html.twig');
    }

    //Travail de Selim: récupère le nom d une matière
    public function getNomMatiereAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT * FROM `matiere` WHERE id=?";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $id);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }

    //Travail de Selim: récupère les notes d un élève
    public function getValeurNoteAction($ideleve)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT * FROM `notes` WHERE eleve_id=?";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $ideleve);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }

}
