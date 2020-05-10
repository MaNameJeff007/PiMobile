<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Notification;
use ScolariteBundle\Entity\Seance;
use ScolariteBundle\Repository\SeanceRepository;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use ScolariteBundle\Form\RechercheType;
/**
 * Seance controller.
 *
 * @Route("seance")
 */
class SeanceController extends Controller
{
    /**
     * Lists all seance entities.
     *
     * @Route("/", name="seance_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $seances = $em->getRepository('ScolariteBundle:Seance')->findAll();

        return $this->render('seance/index.html.twig', array(
            'seances' => $seances,
        ));
    }
   /* public function __construct(SeanceRepository $repo)
    {
        $this->repo = $repo;
        //->mailer = $mailer;
    }
*/
    /**
     * Creates a new seance entity.
     *
     * @Route("/new", name="seance_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $seance = new Seance();
        $form = $this->createForm('ScolariteBundle\Form\SeanceType', $seance);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $deb=$seance->getHdeb();
            //$fin=$seance->getHfin();
            $s=$seance->getSalle();
            $j=$seance->getJour();
            // $rep= SeanceRepository;
            $ens=$seance->getEnseignant();
            $class=$seance->getClasse();

            $em1=$this->getDoctrine()->getManager() ->getRepository(Seance::class);
            $check1=$em1->CheckNbS($class,$ens,$j);

            foreach ($check1 as $row)
            {
                $sp=$row['sp'];
                $sc=$row['sc'];

            }

            if ( $sp < 3 and $sc < 2 ) {


                    //$em2 = $this->getDoctrine()->getManager()->getRepository(Seance::class );
                    $check = $em1 ->CheckSalleSeance($s, $deb,$j);
                    if (!count($check)) {
                        if( $deb=='08:00:00')
                        {
                            $seance->setHfin('10:00:00');

                        }
                        elseif ( $deb=='10:15:00')
                        {
                            $seance->setHfin('12:00:00');

                        }
                        elseif ( $deb=='12:00:00')
                        {
                            $seance->setHfin('13:00:00');

                        }
                        elseif ( $deb=='13:00:00')
                        {
                            $seance->setHfin('15:00:00');

                        } else if ( $deb=='15:15:00')
                        {
                            $seance->setHfin('17:00:00');

                        }

                        $em = $this->getDoctrine()->getManager();
                        $em->persist($seance);
                        $em->flush();


                        return $this->redirectToRoute('search_seance', array('id' => $seance->getId()));
                    } else {
                        $this->addFlash(
                            'errorC', 'Cette salle est ocuppé à cet heure !.'
                        );
                        return $this->render('default/errors.html.twig');

                    }



            }
            else{$this->addFlash(
                'error', 'cette journée est pleine !.'
            );
                return $this->render('default/errors.html.twig');}
        }

        return $this->render('seance/new.html.twig', array(
            'seance' => $seance,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a seance entity.
     *
     * @Route("/{id}", name="seance_show")
     * @Method("GET")
     */
    public function showAction(Seance $seance)
    {
        $deleteForm = $this->createDeleteForm($seance);

        return $this->render('seance/show.html.twig', array(
            'seance' => $seance,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing seance entity.
     *
     * @Route("/{id}/edit", name="seance_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Seance $seance)
    {
        $deleteForm = $this->createDeleteForm($seance);
        $editForm = $this->createForm('ScolariteBundle\Form\SeanceType', $seance);

        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
        $s=$seance->getSalle();
        $j=$seance->getJour();
        // $rep= SeanceRepository;
        $ens=$seance->getEnseignant();
        $class=$seance->getClasse();
        $deb=$seance->getHdeb();
        $em1=$this->getDoctrine()->getManager() ->getRepository(Seance::class);
        $check1=$em1->CheckNbS($class,$ens,$j);

        foreach ($check1 as $row)
        {
            $sp=$row['sp'];
            $sc=$row['sc'];

        }

        if ( $sp < 3 and $sc < 2 ) {


            //$em2 = $this->getDoctrine()->getManager()->getRepository(Seance::class );
            $check = $em1 ->CheckSalleSeance($s, $deb,$j);
            if (!count($check)) {
                if( $deb=='08:00:00')
                {
                    $seance->setHfin('10:00:00');

                }
                elseif ( $deb=='10:15:00')
                {
                    $seance->setHfin('12:00:00');

                }
                elseif ( $deb=='12:00:00')
                {
                    $seance->setHfin('13:00:00');

                }
                elseif ( $deb=='13:00:00')
                {
                    $seance->setHfin('15:00:00');

                } else if ( $deb=='15:15:00')
                {
                    $seance->setHfin('17:00:00');

                }


            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('seance_edit', array('id' => $seance->getId()));
        }else {
                    $this->addFlash(
                        'errorC', 'Cette salle est ocuppé à cet heure !.'
                    );
                    return $this->render('default/errors.html.twig');

                }



            }
            else{$this->addFlash(
                'error', 'cette journée est pleine !.'
            );
                return $this->render('default/errors.html.twig');}
        }

        return $this->render('seance/edit.html.twig', array(
            'seance' => $seance,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a seance entity.
     *
     * @Route("/{id}", name="seance_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Seance $seance)
    {
        $form = $this->createDeleteForm($seance);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($seance);
            $em->flush();
        }

        return $this->redirectToRoute('seance_index');
    }

    /**
     * Creates a form to delete a seance entity.
     *
     * @param Seance $seance The seance entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Seance $seance)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('seance_delete', array('id' => $seance->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    public function seanceTAction($id){

        $h= array(
            array(
                "h_d"=>"8:00:00",
                "h_f"=>"10:00:00"
            ),
            array(
                "h_d"=>"10:15:00",
                "h_f"=>"12:00:00"
            ),
            array(
                "h_d"=>"12:00:00",
                "h_f"=>"13:00:00"
            ),
            array(
                "h_d"=>"13:00:00",
                "h_f"=>"15:00:00"
            ),
            array(
                "h_d"=>"15:15:00",
                "h_f"=>"17:00:00"
            ),
        );
        $fs=array(
            array(
                "num"=>"1",
                "js"=>"lundi",
                "hs"=>$h

            ),
            array(
                "num"=>"2",
                "js"=>"mardi",
                "hs"=>$h
            ),
            array(
                "num"=>"3",
                "js"=>"mercredi",
                "hs"=>$h
            ),
            array(
                "num"=>"4",
                "js"=>"jeudi",
                "hs"=>$h
            ),
            array(
                "num"=>"5",
                "js"=>"vendredi",
                "hs"=>$h
            ),
            array(
                "num"=>"6",
                "js"=>"samedi",
                "hs"=>$h
            )
        );
        $em=$this->getDoctrine()->getManager()->getRepository(Seance::class);
        $n=$em->findSeanceC($id);
        //return $this->render('@e/Default/index.html.twig',array("h"=>$h,'st'=>$n));
        return $this->render('seance/emploi.html.twig',array("fs"=>$fs,"h"=>$h,'st'=>$n));
    }

    public function seanceTFAction($id){


        $fs=array(
            array(
                "num"=>"1",
                "js"=>"lundi",


            ),
            array(
                "num"=>"2",
                "js"=>"mardi",

            ),
            array(
                "num"=>"3",
                "js"=>"mercredi",

            ),
            array(
                "num"=>"4",
                "js"=>"jeudi",

            ),
            array(
                "num"=>"5",
                "js"=>"vendredi",

            ),
            array(
                "num"=>"6",
                "js"=>"samedi",

            )
        );
        $em=$this->getDoctrine()->getManager()->getRepository(Seance::class);
        $n=$em->findSeanceC($id);
        //return $this->render('@e/Default/index.html.twig',array("h"=>$h,'st'=>$n));
        return $this->render('seance/empEleveF.html.twig',array("fs"=>$fs,'st'=>$n));
    }
    public function SeanceEAction($id){

        $h= array(
            array(
                "h_d"=>"8:00:00",
                "h_f"=>"10:00:00"
            ),
            array(
                "h_d"=>"10:15:00",
                "h_f"=>"12:00:00"
            ),
            array(
                "h_d"=>"12:00:00",
                "h_f"=>"13:00:00"
            ),
            array(
                "h_d"=>"13:00:00",
                "h_f"=>"15:00:00"
            ),
            array(
                "h_d"=>"15:15:00",
                "h_f"=>"17:00:00"
            ),
        );
        $fs=array(
            array(
                "num"=>"1",
                "js"=>"lundi",
                "hs"=>$h

            ),
            array(
                "num"=>"2",
                "js"=>"mardi",
                "hs"=>$h
            ),
            array(
                "num"=>"3",
                "js"=>"mercredi",
                "hs"=>$h
            ),
            array(
                "num"=>"4",
                "js"=>"jeudi",
                "hs"=>$h
            ),
            array(
                "num"=>"5",
                "js"=>"vendredi",
                "hs"=>$h
            ),
            array(
                "num"=>"6",
                "js"=>"samedi",
                "hs"=>$h
            )
        );
        $em=$this->getDoctrine()->getManager()->getRepository(Seance::class);
        $n=$em->findSeanceE($id);
        //return $this->render('@e/Default/index.html.twig',array("h"=>$h,'st'=>$n));
        return $this->render('seance/empE.html.twig',array("fs"=>$fs,"h"=>$h,'st'=>$n));
    }

    public function supprimerSeanceAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Seance::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('search_seance');
    }

    public function searchAction(Request $request)
    {
        $formation=new Seance();
        $form=$this->createForm(RechercheType::class, $formation);
        $form=$form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {

            $seances =$this->getDoctrine()
                ->getRepository(Seance::class)
                ->findBy(array('classe'=>$formation->getClasse() ));
            return $this->render('seance/index.html.twig',  array("form"=>$form->createView(),"seances"=>$seances));
        }

        else
        {
            $formations =$this->getDoctrine()->getRepository(Seance::class)->findAll();
            return $this->render('seance/index.html.twig',  array("form"=>$form->createView(),"seances"=>$formations));
        }

    }

}




