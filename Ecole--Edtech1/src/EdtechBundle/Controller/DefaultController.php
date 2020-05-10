<?php

namespace EdtechBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;
use Dompdf\Options;
use Dompdf\Dompdf;
use Symfony\Component\DomCrawler\Crawler;


class DefaultController extends Controller
{
    public function indexAction()
    {
        $vars= "haha";
      /*  $this->get('knp_snappy.pdf')->generateFromHtml(
            $this->renderView(
                'base.html.twig',
                array(
                    'some'  => $vars
                )
            ),
            'c:\\wamp\\www\\untitled2\\boof.pdf'
        );

        $html = $this->renderView('base.html.twig', array('monUser' => "wafa"));

        return new Response(
            $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
            200,
            array(
                'Content-Type'          => 'application/pdf',
                'Content-Disposition'   => 'attachment; filename="file.pdf"'
            )
        );*/
       return $this->render('@App/Default/base.html.twig');
    }
  /*  public function toPdfAction()
    {
        // On récupère l'objet à afficher (rien d'inconnu jusque là)
        //$objectsRepository = $this->getDoctrine()->getRepository('TestBundle:Object');
        // $object = $objectsRepository->findOneById($objectId);
        $object = 1;
        // On crée une  instance pour définir les options de notre fichier pdf
        $options = new Options();
        // Pour simplifier l'affichage des images, on autorise dompdf à utiliser
        // des  url pour les nom de  fichier
        $options->set('isRemoteEnabled', TRUE);
        // On crée une instance de dompdf avec  les options définies
        $dompdf = new Dompdf($options);
        // On demande à Symfony de générer  le code html  correspondant à
        // notre template, et on stocke ce code dans une variable

        $em = $this->getDoctrine()->getManager();

        $courses = $em->getRepository('EdtechBundle:course')->findAll();
        $html = $this->renderView(
            'course\pdf.html.twig',
            array('courses' => $courses)
        );
        //$crawler = new Crawler($html);
        //$crawler = $crawler->filterXPath('descendant-or-self::body/table')->html();
        //dump($crawler);

        // On envoie le code html  à notre instance de dompdf
        $dompdf->loadHtml($html);
        // On demande à dompdf de générer le  pdf
        $dompdf->render();
        // On renvoie  le flux du fichier pdf dans une  Response pour l'utilisateur
        return new Response ($dompdf->stream());
    }*/
}
