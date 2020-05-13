<?php

namespace ForumBundle\Controller;

use AppBundle\Entity\User;
use ForumBundle\Entity\Commentaire;
use ForumBundle\Entity\Likes;
use ForumBundle\Entity\Signaler;
use ForumBundle\Entity\Sujet;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
/**
 * Sujet controller.
 *
 * @Route("sujet")
 */
class SujetController extends Controller
{
    /**
     * Lists all sujet entities.
     *
     * @Route("/", name="sujet_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $sujets = $em->getRepository('ForumBundle:Sujet')->findBy(array(),array('score' => 'DESC'));

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $aa = $em->getRepository(Likes::class)->findBy(array("createur"=>$user->getId()));
        return $this->render('sujet/index.html.twig', array(
            'sujets' => $sujets,'aa' => $aa
        ));
    }

    /**
     * Lists all sujet entities.
     *
     * @Route("/back/", name="sujet_index_back")
     * @Method("GET")
     */
    public function indexBAction()
    {
        $em = $this->getDoctrine()->getManager();

        $sujets = $em->getRepository('ForumBundle:Sujet')->findBy(array(),array('score' => 'DESC'));

        return $this->render('sujet/indexback.html.twig', array(
            'sujets' => $sujets,
        ));
    }

    /**
     * Creates a new sujet entity.
     *
     * @Route("/new", name="sujet_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $sujet = new Sujet();
        $form = $this->createForm('ForumBundle\Form\SujetType', $sujet);
        $form->handleRequest($request);
        $sujet->setCreateur($user->getId());
        $sujet->setDate(new \DateTime('now'));
        $sujet->setScore(0);
        $sujet->setVues(0);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($sujet);
            $em->flush();

            return $this->redirectToRoute('sujet_index', array('id' => $sujet->getId()));
        }

        return $this->render('sujet/new.html.twig', array(
            'sujet' => $sujet,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a sujet entity.
     *
     * @Route("/{id}", name="sujet_show")
     * @Method("GET")
     */
    public function showAction(Request $request,Sujet $sujet)
    {
        $sujet->setVues($sujet->getVues()+1);
        $emm = $this->getDoctrine()->getManager();
        $emm->persist($sujet);
        $emm->flush();

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $aa = $emm->getRepository(Likes::class)->findBy(array("createur"=>$user->getId()));
        $this->get('session')->set('id', $sujet->getId());
        $deleteForm = $this->createDeleteForm($sujet);
        $commentaire = new Commentaire();
        $commentaires = $emm->getRepository('ForumBundle:Commentaire')->findBy(array("sujet"=>$sujet),array('score' => 'DESC'));
        $form = $this->createForm('ForumBundle\Form\CommentaireType', $commentaire);
        $form->handleRequest($request);
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $commentaire->setCreateur($user->getId());
        $commentaire->setDate(new \DateTime('now'));
        $commentaire->setScore(0);
        $commentaire->setSujet($sujet);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($commentaire);
            $em->flush();

            return $this->render('sujet/show.html.twig',array(
                'sujet' => $sujet,
                'delete_form' => $deleteForm->createView(),
                'commentaires' => $commentaires,
                'commentaire' => $commentaire,'aa' => $aa,
                'form' => $form->createView(),
            ));
        }

        return $this->render('sujet/show.html.twig',array(
            'sujet' => $sujet,
            'delete_form' => $deleteForm->createView(),
            'commentaires' => $commentaires,
            'commentaire' => $commentaire,'aa' => $aa,
            'form' => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing sujet entity.
     *
     * @Route("/{id}/edit", name="sujet_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Sujet $sujet)
    {
        $deleteForm = $this->createDeleteForm($sujet);
        $editForm = $this->createForm('ForumBundle\Form\SujetType', $sujet);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('sujet_index', array('id' => $sujet->getId()));
        }

        return $this->render('sujet/edit.html.twig', array(
            'sujet' => $sujet,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a sujet entity.
     *
     * @Route("/{id}", name="sujet_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Sujet $sujet)
    {
        $form = $this->createDeleteForm($sujet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($sujet);
            $em->flush();
        }

        return $this->redirectToRoute('sujet_index');
    }

    public function supprimerAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Sujet::class)->find($id);
        $dza=$en->getRepository(Commentaire::class)->findbyfa($aa);
        $s=$this->getDoctrine()->getRepository(Sujet::class)->find($id);
        $ss = $en->getRepository(Signaler::class)->findOneBy(array("sujet"=>$s));
        $en->remove($s);
        $en->remove($ss);
        $en->flush();
        return $this->redirectToRoute('sujet_index');
    }

    public function supprimerBAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Sujet::class)->find($id);
        $dza=$en->getRepository(Commentaire::class)->findbyfa($aa);
        $s=$this->getDoctrine()->getRepository(Sujet::class)->find($id);
        $en->remove($s);
        $en->flush();
        return $this->redirectToRoute('sujet_index_back');
    }

    public function getallAction()
    {
        $sujet = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Sujet')->findAll();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($sujet);
        return new JsonResponse($formatted);
    }

    public function getAction($texte)
    {
        $sujet = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Sujet')->findBy(array('titre'=>$texte));
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($sujet);
        return new JsonResponse($formatted);
    }

    public function modifMobileAction($id,$titre,$description)
    {
        $sujet = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Sujet')->find($id);
        $sujet->setTitre($titre);
        $sujet->setDescription($description);
        $this->getDoctrine()->getManager()->flush();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($sujet);
        return new JsonResponse($formatted);
    }

    public function newMobileAction($titre,$description,$createur_id)
    {
        $em = $this->getDoctrine()->getManager();
        $sujet = new Sujet();
        $user=$this->getDoctrine()->getRepository(User::class)->find($createur_id);
        $sujet->setTitre($titre);
        $sujet->setDescription($description);
        $sujet->setCreateur($user->getId());
       $sujet->setDate(new \DateTime('now'));
        $sujet->setScore(0);
        $sujet->setVues(0);
        $em->persist($sujet);
        $em->flush();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($sujet);
        return new JsonResponse($formatted);
    }

    public function deleteMobileAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Sujet::class)->find($id);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aa);
        $en->remove($aa);
        $en->flush();
        return new JsonResponse($formatted);
    }



    /**
     * Creates a form to delete a sujet entity.
     *
     * @param Sujet $sujet The sujet entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Sujet $sujet)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('sujet_delete', array('id' => $sujet->getId())))
            ->setMethod('DELETE')
            ->getForm()
            ;
    }
}
