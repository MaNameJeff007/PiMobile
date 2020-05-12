<?php

namespace ScolariteBundle\Controller;

use EnseignantBundle\EnseignantBundle;
use EnseignantBundle\Entity\Bulletin;
use EnseignantBundle\Entity\Notes;
use ScolariteBundle\Entity\Coeff;
use EnseignantBundle\Repository;
use ScolariteBundle\Entity\Moyennesgenerales;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

/**
 * Coeff controller.
 *
 * @Route("coeff")
 */
class CoeffController extends Controller
{

    public function editCoeffApiAction(Request $request,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $cl = $this->getDoctrine()->getRepository(Coeff::class)->find($id);
        $matiere = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Matiere')
            ->findOneByNom($request->get('matiere'));

        $niv = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->findOneByLibelle($request->get('niveau'));

        $cl->setNiveau($niv);
        $cl->setMatiere($matiere);


        $cl->setValeur($request->get('valeur'));

        $em->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('matiere','niveau'));
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($cl, 'json');
        $formatted1 = $serializer->normalize($cl);
        return new JsonResponse($formatted1);
    }

    public function supprimerCoeffApiAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Coeff::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants','matiere'));
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($c, 'json');
        $formatted1 = $serializer->normalize($c);
        return new JsonResponse($formatted1);

    }
    public function allCAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Coeff')
            ->findAll();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('nbH','eleves','enseignants','capacite','moyennes','notes'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($tasks, 'json');
        $formatted1 = $serializer->normalize($tasks);
        return new JsonResponse($formatted1);
    }

    public function newCoeffApiAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $cl = new Coeff();
        $matiere = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Matiere')
            ->findOneByNom($request->get('matiere'));

        $niv = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->findOneByLibelle($request->get('niveau'));

        $cl->setNiveau($niv);
        $cl->setMatiere($matiere);


        $cl->setValeur($request->get('valeur'));
        $em->persist($cl);
        $em->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('matiere','niveau'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($cl, 'json');
        $formatted1 = $serializer->normalize($cl);
        return new JsonResponse($formatted1);
    }
    /**
     * Lists all coeff entities.
     *
     * @Route("/", name="coeff_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $coeffs = $em->getRepository('ScolariteBundle:Coeff')->findAll();

        return $this->render('coeff/index.html.twig', array(
            'coeffs' => $coeffs,
        ));
    }

    /**
     * Creates a new coeff entity.
     *
     * @Route("/new", name="coeff_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $coeff = new Coeff();
        $form = $this->createForm('ScolariteBundle\Form\CoeffType', $coeff);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $cn = $coeff->getNiveau();
            $cf=$coeff->getValeur();
            $cc=$coeff->getMatiere();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
            $check = $em1->findCoeff($cn,$cc);

            if (!count($check)) {



            $em = $this->getDoctrine()->getManager();
            $em->persist($coeff);
            $em->flush();

            return $this->redirectToRoute('coeff_index', array('id' => $coeff->getId()));
        }else{
                $this->addFlash(
                    'errorC ', 'Les classe du meme niveau doivent avoir le meme coefficient pour toute les matières !'
                );
                return $this->render('default/errors.html.twig');
            }
        }

        return $this->render('coeff/new.html.twig', array(
            'coeff' => $coeff,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a coeff entity.
     *
     * @Route("/{id}", name="coeff_show")
     * @Method("GET")
     */
    public function showAction(Coeff $coeff)
    {
        $deleteForm = $this->createDeleteForm($coeff);

        return $this->render('coeff/show.html.twig', array(
            'coeff' => $coeff,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing coeff entity.
     *
     * @Route("/{id}/edit", name="coeff_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Coeff $coeff)
    {
        $deleteForm = $this->createDeleteForm($coeff);
        $editForm = $this->createForm('ScolariteBundle\Form\CoeffType', $coeff);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {

            $cn = $coeff->getNiveau();
            $cf=$coeff->getValeur();
            $cc=$coeff->getMatiere();
            $em1 = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
            $check = $em1->findCoeff($cn,$cc);

            if (!count($check)) {



                $this->getDoctrine()->getManager()->flush();


                return $this->redirectToRoute('coeff_edit', array('id' => $coeff->getId()));
            }else{
                $this->addFlash(
                    'errorC ', 'Les classe du meme niveau doivent avoir le meme coefficient pour toute les matières !'
                );
                return $this->render('default/errors.html.twig');
            }
        }


        return $this->render('coeff/edit.html.twig', array(
            'coeff' => $coeff,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a coeff entity.
     *
     * @Route("/{id}", name="coeff_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Coeff $coeff)
    {
        $form = $this->createDeleteForm($coeff);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($coeff);
            $em->flush();
        }

        return $this->redirectToRoute('coeff_index');
    }

    /**
     * Creates a form to delete a coeff entity.
     *
     * @param Coeff $coeff The coeff entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Coeff $coeff)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('coeff_delete', array('id' => $coeff->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
    public function supprimerCAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Coeff::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('coeff_index');
    }



    public function CAction(Request $request,$eleve,$trimestre,$classeE)
    {
        $moyenne = new Bulletin();
        $moyenne->setEleve($eleve);
        $moyenne->setClasse($classeE);
        $moyenne->setTrimestre($trimestre);
        $em = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
      /*  $niv = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->findOneById($classeE);*/
        $results = $em->MEleve($classeE, $trimestre,$eleve);
        //$results = $em->REleve1($eleve, $tr);

        foreach ($results as $row) {
                $res=$row['moyG'];
                //$p=$em->REleve2($res,$tr);
            $moyenne->setMoyenne($res);
                //$m = $row['moyG'];

            }
        $em = $this->getDoctrine()->getManager()->getRepository(Coeff::class);
        $ch = $em-> TrouverB($eleve,$trimestre);
        if (!count($ch))
        {
            $em = $this->getDoctrine()->getManager();
        
            $em->persist($moyenne);
            
            $em->flush();

            
        }
        else{

             foreach ($ch as $row) {
               
                $res = $row['id'];
                $club=$this->getDoctrine()->getRepository(Bulletin::class)->find($res);
                $en=$this->getDoctrine()->getManager();
                /* $niv = $this->getDoctrine()->getManager()
                     ->getRepository('ScolariteBundle:Classe')
                     ->findOneById($classeE);*/

                $results = $em->MEleve($classeE, $trimestre,$eleve);
       

                   foreach ($results as $row) {
                    $res=$row['moyG'];
                
                     $club->setMoyenne($res);

                   }

                      $en->flush();
            }

        }

        $em=$this->getDoctrine()->getManager()->getRepository(Coeff::class);

        $hh=$em->TrouverMatiere($trimestre,$eleve);
        
        $n=$em-> TrouverNotes($trimestre,$eleve);
        $e=$em->TrouverE($eleve);

        //$html = $this->renderView('bulletin/bulletin.html.twig',array('fr'=>$n,'b'=>$ch));
        $html = $this->renderView('bulletin/bulletin.html.twig',array('fr'=>$n,'b'=>$ch,'hh'=>$hh,'e'=>$e,$trimestre));
        //$html = $this->renderView('default/bulletin.html.twig');

        $filename = sprintf('Bulletin-%s.pdf', date('Y-m-d'));

        return new Response(
            $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
            200,
            [
                'Content-Type'        => 'application/pdf',
                'Content-Disposition' => sprintf('attachment; filename="%s"', $filename),
            ]
        );


//inline

    }


}
