<?php

namespace EdtechBundle\Controller;

use EdtechBundle\Entity\exercice;
use EdtechBundle\Entity\score;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;use Symfony\Component\HttpFoundation\Request;
use Ob\HighchartsBundle\Highcharts\Highchart;

/**
 * Exercice controller.
 *
 * @Route("back/exercice")
 */
class exerciceController extends Controller
{
    /**
     * Lists all exercice entities.
     *
     * @Route("/", name="exercice_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $exercices = $em->getRepository('EdtechBundle:exercice')->findAll();

        return $this->render('exercice/index.html.twig', array(
            'exercices' => $exercices,
        ));
    }

    /**
     * Creates a new exercice entity.
     *
     * @Route("/new", name="exercice_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $exercice = new Exercice();
        $form = $this->createForm('EdtechBundle\Form\exerciceType', $exercice);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($exercice);
            $em->flush();

            return $this->redirectToRoute('exercice_show', array('id' => $exercice->getId()));
        }

        return $this->render('exercice/new.html.twig', array(
            'exercice' => $exercice,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a exercice entity.
     *
     * @Route("/{id}", name="exercice_show")
     * @Method("GET")
     */
    public function showAction(exercice $exercice)
    {
        $deleteForm = $this->createDeleteForm($exercice);

        return $this->render('exercice/show.html.twig', array(
            'exercice' => $exercice,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing exercice entity.
     *
     * @Route("/{id}/edit", name="exercice_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, exercice $exercice)
    {
        $deleteForm = $this->createDeleteForm($exercice);
        $editForm = $this->createForm('EdtechBundle\Form\exerciceType', $exercice);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('exercice_edit', array('id' => $exercice->getId()));
        }

        return $this->render('exercice/edit.html.twig', array(
            'exercice' => $exercice,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a exercice entity.
     *
     * @Route("/{id}", name="exercice_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, exercice $exercice)
    {
        $form = $this->createDeleteForm($exercice);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($exercice);
            $em->flush();
        }

        return $this->redirectToRoute('exercice_index');
    }

    /**
     * Creates a form to delete a exercice entity.
     *
     * @param exercice $exercice The exercice entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(exercice $exercice)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('exercice_delete', array('id' => $exercice->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    public function profilExAction(Request $request,$score){
        $em = $this->getDoctrine()->getEntityManager();
       // $score= 2;
        //dump($score);


           // $score = $request->query->get('sc');


            $now = new \DateTime('now');
            $sc = new score();
            dump($score);
            $sc->setDate($now);
            $sc->setScore((int)$score);
            $sc->setUser($this->getUser());


            $em->persist($sc);
            $em->flush();

        $scores = $em->getRepository('EdtechBundle:score')->findBy(array('user' => $this->getUser()));

        $nb = array();
        $i = 0;
        foreach ($scores as $score2){
            $nb[$i] = $score2->getScore();
            $i++;
        }

        $series = array(
            array("name" => "score",    "data" => $nb)
        );

        $ob = new Highchart();
        $ob->chart->renderTo('linechart');  // The #id of the div where to render the chart
        $ob->title->text('Evolution des scores');
        $ob->xAxis->title(array('text'  => "Score"));
        $ob->yAxis->title(array('text'  => "Notes"));
        $ob->series($series);



        return $this->render('front/profilex.html.twig',
            array('score'=>$score,
                'chart' => $ob));

    }
}
