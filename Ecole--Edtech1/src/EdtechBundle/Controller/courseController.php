<?php

namespace EdtechBundle\Controller;

use Dompdf\Dompdf;
use Dompdf\Options;
use EdtechBundle\Entity\course;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Ob\HighchartsBundle\Highcharts\Highchart;
use Symfony\Component\HttpFoundation\Response;
use Zend\Json\Expr;


/**
 * Course controller.
 *
 * @Route("course")
 */
class courseController extends Controller
{
    /**
     * Lists all course entities.
     *
     * @Route("/", name="course_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $courses = $em->getRepository('EdtechBundle:course')->findAll();

        return $this->render('course/index.html.twig', array(
            'courses' => $courses,
        ));
    }

    /**
     * Creates a new course entity.
     *
     * @Route("/new", name="course_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $course = new Course();
        $form = $this->createForm('EdtechBundle\Form\courseType', $course);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $contenuFile = $form->get('contenu')->getData();
            if ($contenuFile) {
                $originalFilename = pathinfo($contenuFile->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = transliterator_transliterate('Any-Latin; Latin-ASCII; [^A-Za-z0-9_] remove; Lower()', $originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$contenuFile->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $contenuFile->move(
                        $this->getParameter('contenu_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $course->setContenu($newFilename);
            }

            $em = $this->getDoctrine()->getManager();
            $em->persist($course);
            $em->flush();

            return $this->redirectToRoute('course_show', array('id' => $course->getId()));
        }

        return $this->render('course/new.html.twig', array(
            'course' => $course,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a course entity.
     *
     * @Route("/{id}", name="course_show")
     * @Method("GET")
     */
    public function showAction(course $course)
    {
        $deleteForm = $this->createDeleteForm($course);

        return $this->render('course/show.html.twig', array(
            'course' => $course,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing course entity.
     *
     * @Route("/{id}/edit", name="course_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, course $course)
    {
        $deleteForm = $this->createDeleteForm($course);
        $editForm = $this->createForm('EdtechBundle\Form\courseType', $course);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $contenuFile = $editForm->get('contenu')->getData();
            if ($contenuFile) {
                $originalFilename = pathinfo($contenuFile->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = transliterator_transliterate('Any-Latin; Latin-ASCII; [^A-Za-z0-9_] remove; Lower()', $originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$contenuFile->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $contenuFile->move(
                        $this->getParameter('contenu_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $course->setContenu($newFilename);
            }

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('course_edit', array('id' => $course->getId()));
        }

        return $this->render('course/edit.html.twig', array(
            'course' => $course,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a course entity.
     *
     * @Route("/{id}", name="course_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, course $course)
    {
        $form = $this->createDeleteForm($course);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($course);
            $em->flush();
        }

        return $this->redirectToRoute('course_index');
    }

    /**
     * Creates a form to delete a course entity.
     *
     * @param course $course The course entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(course $course)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('course_delete', array('id' => $course->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(course::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('course_index');
    }

    public function coursTypeAction($type){
       // $type =  $request->query->get('type');
        $em = $this->getDoctrine()->getManager();

        $courses = $em->getRepository('EdtechBundle:course')->findAll();
        $arr =  array();
        $i=0;
        foreach ($courses as $course){
            if (($course -> getTypeIntelligence()) == $type){
                $arr[$i]= $course;
            }
            $i++;
        }
        dump($arr);
        return $this->render('Front/listcours.html.twig', array(
            'courses' => $arr,
        ));
       /* return $this->render('Front/coursintelligence1.html.twig', array(
            'courses' => "courses",
        ));*/
    }

    public function coursdetailAction($course){
        // $type =  $request->query->get('type');
        $em = $this->getDoctrine()->getManager();
        $course = $em -> getRepository('EdtechBundle:course')->find($course);

        $exercice = $em -> getRepository('EdtechBundle:exercice')->findBy(array('course' => $course));
        $a = "/public/uploads/cours/" . $course->getContenu();
         return $this->render('Front/coursintelligence1.html.twig', array(
             'content' => $a,
             'exercices'=> $exercice,
             'course' => $course->getId()
         ));
    }
    public function chartAction()
    {
        $em = $this->getDoctrine()->getManager();
        $courses = $em -> getRepository('EdtechBundle:course')->findAll();
        $coursesNom = array();
        $nbExercices = array();
        $i = 0;
        foreach ($courses as $course){
            $exercice = $em -> getRepository('EdtechBundle:exercice')->findBy(array('course' => $course->getId()));
            $nb = count($exercice);
            $coursesNom[$i] = $course->getNom();
            $nbExercices[$i] = $nb;
            $i++;
        }


        $series = array(
            array(
                'name' => 'exercice',
                'type' => 'column',
                'color' => '#4572A7',
                'yAxis' => 1,
                'data' => $nbExercices,
            ),

        );
        $yData = array(
            array(



            ),
            array(
                'labels' => array(
                    'formatter' => new Expr('function () { return this.value + " exercices" }'),
                    'style' => array('color' => '#4572A7')
                ),
                'gridLineWidth' => 0,
                'title' => array(
                    'text' => 'exercice',
                    'style' => array('color' => '#4572A7')
                ),
            ),
        );

        $categories = $coursesNom;
        $ob = new Highchart();
        $ob->chart->renderTo('linechart'); // The #id of the div where to render the chart
        $ob->chart->type('column');
        $ob->title->text('Number of exercices per course');
        $ob->xAxis->categories($categories);
        $ob->yAxis($yData);
        $ob->legend->enabled(false);
        $formatter = new Expr('function () {
var unit = {
"Rainfall": "exercice",
"exercice": "exercices"
}[this.series.name];
return this.x + ": <b>" + this.y + "</b> " + unit;
}');
        $ob->tooltip->formatter($formatter);
        $ob->series($series);
        return $this->render('course/chart.html.twig', array(
            'chart' => $ob
        ));
    }


    public function getFourRandomAnswers($answer){
        $em = $this ->getDoctrine()-> getEntityManager();
        $quizs = $em -> getRepository('EdtechBundle:exercice')->findall();
        $answers[] = array();
        $i= 0;
        foreach ($quizs as $quiz){
            $answers[$i] = $quiz ->getReponse();
            $i++;
        }
        //$rand_keys = array_rand($answers, 4);
        //dump($rand_keys);
        shuffle($answers);
        $r = rand(0,4);
        $randArray = array();
        if ($r>0) {
            $a = array_slice($answers, 0, $r );
            $randArray = array_merge($randArray,$a);
        }
        $randArray[$r] = $answer;
        if ($r<=4) {
            $a= array_slice($answers, $r, 4);
            $randArray = array_merge($randArray,$a);
        }

        //dump($randArray);
        return $randArray;
    }
    public function toPdfAction()
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
    }

    public function courseExerciceAction($course,Request $request){
        $em = $this->getDoctrine()->getEntityManager();
        $exercices = $em->getRepository('EdtechBundle:exercice')->findBy(array('course' => $course));

        $data[] = array();$i=0;
        foreach ( $exercices as $ex){
            $rand = $this->getFourRandomAnswers($ex->getReponse());
            // dump($quiz-> getReponse());
            $data[$i] = [
                'id' => $ex -> getId(),
                'question' => $ex -> getQuestion(),
//                  'options' => [array("a"=>"pk","b"=>"aaaaa","c"=>"wafa","d"=>"boofy")],
                'options' => [array("a"=>$rand[0],"b"=>$rand[1],"c"=>$rand[2],"d"=>$rand[3])],

                'answer' => $ex -> getReponse(),
                'status' => "",
                'sc' => $ex-> getScore(),
                'score' => 0,
            ];
            $i++;
        }

        $response = new JsonResponse(array('JS'=>$data));

        if ($request->isMethod('POST')){
            return $this->render('base.html.twig');
        }
        return $this->render('front/exercice.html.twig',
            array('quizs'=>$response ->getContent()));
}


}
