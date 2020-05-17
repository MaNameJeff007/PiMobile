<?php

namespace GestionBundle\Controller;

use GestionBundle\Entity\Attestation;
use GestionBundle\Entity\Reclamation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use UserBundle\Entity\User;

/**
 * Attestation controller.
 *
 * @Route("attestation")
 */
class AttestationController extends Controller
{
    /**
     * Lists all attestation entities.
     *
     * @Route("/", name="attestation_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $attestations = $em->getRepository('GestionBundle:Attestation')->findBy(array('parent' => $user->getId()));

        return $this->render('attestation/index.html.twig', array(
            'attestations' => $attestations,
        ));
    }

    /**
     * Creates a new attestation entity.
     *
     * @Route("/new", name="attestation_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request,$parent)
    {
        $attestation = new Attestation();

        $p=$this->getDoctrine()->getManager()->getRepository(\AppBundle\Entity\User::class)->find($parent);
        $attestation->setParent($p);
        $attestation->setDate(new \DateTime('now'));
        $attestation->setEtat("non traitee");

            $em = $this->getDoctrine()->getManager();
            $em->persist($attestation);
            $em->flush();

            return $this->redirectToRoute('attestationindex', array('id' => $attestation->getId()));


    }

    /**
     * Finds and displays a attestation entity.
     *
     * @Route("/{id}", name="attestation_show")
     * @Method("GET")
     */
    public function showAction(Attestation $attestation)
    {
        $deleteForm = $this->createDeleteForm($attestation);

        return $this->render('attestation/show.html.twig', array(
            'attestation' => $attestation,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing attestation entity.
     *
     * @Route("/{id}/edit", name="attestation_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Attestation $attestation)
    {
        $deleteForm = $this->createDeleteForm($attestation);
        $editForm = $this->createForm('GestionBundle\Form\AttestationType', $attestation);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('attestation_index', array('id' => $attestation->getId()));
        }

        return $this->render('attestation/edit.html.twig', array(
            'attestation' => $attestation,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }



    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Attestation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('attestationindex');
    }

    public function traiterAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Attestation::class)->find($id);
        $user=$this->getDoctrine()->getRepository(\AppBundle\Entity\User::class)->find($c->getIduser());
        $en=$this->getDoctrine()->getManager();
        $c->setEtat("traitee");
        $en->persist($c);
        $en->flush();

        $username='topisland123@gmail.com';
        $message= \Swift_Message::newInstance()
            ->setSubject('Attestation')
            ->setFrom($username)
            ->setTo($user->getEmail())
            ->setBody('Votre attestation a été traitée');
        $this->get('mailer')->send($message);


        return $this->redirectToRoute('attestationadmin');
    }

    public function indexadminAction()
    {
        $em = $this->getDoctrine()->getManager();

        $attestations = $em->getRepository('GestionBundle:Attestation')->findAll();

        return $this->render('attestation/adminconsulter.html.twig', array(
            'attestations' => $attestations,
        ));
    }

    public function adminsupprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Attestation::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        return $this->redirectToRoute('attestationadmin');
    }
    public function getAllAction($id)
    {
        $a=$this->getDoctrine()->getManager()->getRepository(Attestation::class)->findBy(array('parent' => $id));
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer2=new Serializer($normalizers);
        $formatted = $serializer2->normalize($a);
        return new JsonResponse($formatted);
    }

    public function newMobileAction($parent,$enf)
    {
        $att = new Attestation();
        $att->setDate(new \DateTime('now'));
        $att->setEtat("non traitee");
        $att->setEnfant($enf);
        $em = $this->getDoctrine()->getManager();
        $p=$em->getRepository(\AppBundle\Entity\User::class)->find($parent);
        $att->setParent($p);
        $em->persist($att);
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer2=new Serializer($normalizers);
        $formatted = $serializer2->normalize($att);
        return new JsonResponse($formatted);
    }

    public function deleteMobileAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Attestation::class)->find($id);
        $en->remove($aa);
        $en->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer2=new Serializer($normalizers);
        $formatted = $serializer2->normalize($aa);
        return new JsonResponse($formatted);
    }

    /**
     * Creates a form to delete a attestation entity.
     *
     * @param Attestation $attestation The attestation entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Attestation $attestation)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('attestation_delete', array('id' => $attestation->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
