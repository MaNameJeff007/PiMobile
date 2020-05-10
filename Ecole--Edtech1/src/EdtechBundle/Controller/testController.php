<?php

namespace EdtechBundle\Controller;

use EdtechBundle\Entity\course;
use EdtechBundle\Entity\test;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;

/**
 * Test controller.
 *
 * @Route("/back/test")
 */
class testController extends Controller
{
    /**
     * Lists all test entities.
     *
     * @Route("/", name="test_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $tests = $em->getRepository('EdtechBundle:test')->findAll();

        return $this->render('test/index.html.twig', array(
            'tests' => $tests,
        ));
    }

    /**
     * Creates a new test entity.
     *
     * @Route("/new", name="test_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $test = new Test();
        $form = $this->createForm('EdtechBundle\Form\testType', $test);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($test);
            $em->flush();

            return $this->redirectToRoute('test_show', array('id' => $test->getId()));
        }

        return $this->render('test/new.html.twig', array(
            'test' => $test,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a test entity.
     *
     * @Route("/{id}", name="test_show")
     * @Method("GET")
     */
    public function showAction(test $test)
    {
        $deleteForm = $this->createDeleteForm($test);

        return $this->render('test/show.html.twig', array(
            'test' => $test,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing test entity.
     *
     * @Route("/{id}/edit", name="test_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, test $test)
    {
        $deleteForm = $this->createDeleteForm($test);
        $editForm = $this->createForm('EdtechBundle\Form\testType', $test);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('test_edit', array('id' => $test->getId()));
        }

        return $this->render('test/edit.html.twig', array(
            'test' => $test,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a test entity.
     *
     * @Route("/{id}", name="test_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, test $test)
    {
        $form = $this->createDeleteForm($test);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($test);
            $em->flush();
        }

        return $this->redirectToRoute('test_index');
    }

    /**
     * Creates a form to delete a test entity.
     *
     * @param test $test The test entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(test $test)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('test_delete', array('id' => $test->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    /**
     * Deletes a test entity.
     *
     * @Route("/{id}/delete", name="test_supp")
     * @Method("POST")
     */
    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(test::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('test_index');
    }

    public function indexFrontAction()
    {
        $em = $this->getDoctrine()->getManager();

        $tests = $em->getRepository('EdtechBundle:test')->findAll();

        return $this->render('quiz/quiz.html.twig', array(
            'tests' => $tests,
        ));
    }

    public function getFourRandomAnswers($answer){
        $em = $this ->getDoctrine()-> getEntityManager();
        $quizs = $em -> getRepository('EdtechBundle:test')->findall();
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

    /**
     * Posts a test entity.
     *
     * @Method("POST")
     */
    public function quizJsonAction(Request $request)
    {


           $em = $this->getDoctrine()->getEntityManager();
           $quizs = $em->getRepository('EdtechBundle:test')->findall();

           $data[] = array();$i=0;
           foreach ( $quizs as $quiz){
               $rand = $this->getFourRandomAnswers($quiz->getReponse());
              // dump($quiz-> getReponse());
               $data[$i] = [
                   'id' => $quiz -> getId(),
                   'question' => $quiz -> getQuestion(),
//                   'options' => [array("a"=>"pk","b"=>"aaaaa","c"=>"wafa","d"=>"boofy")],
                   'options' => [array("a"=>$rand[0],"b"=>$rand[1],"c"=>$rand[2],"d"=>$rand[3])],

                   'answer' => $quiz -> getReponse(),
                   'status' => "",
                   'score' => 0,
                   'typeIn'=> $quiz -> getTypeIntell()

               ];
               $i++;
           }
           $response = new JsonResponse(array('JS'=>$data));
           //dump($data);
   //        $datas = json_encode($data); // formater le résultat de la requête en json

           //$response->headers->set('Content-Type', 'application/json');
           //$response->setContent($datas);

         //  return $response;
        if ($request->isMethod('POST')){
            return $this->render('base.html.twig');
        }
           return $this->render('quiz/quiz.html.twig',
               array('quizs'=>$response ->getContent()));
       }

    /**
     * @Route("/accueil", name="accueil")
     * @Method("POST")
     */
    public function accueilAction( Request $request)
    {
        if($request->isXmlHttpRequest()) {
            dump("ok");
            $idMagasin = $request->request->get('id');
           // dump($idMagasin);
        }
        return $this->redirectToRoute('edtech_homepage');
    }

}
