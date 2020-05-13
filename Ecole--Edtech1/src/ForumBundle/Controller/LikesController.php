<?php

namespace ForumBundle\Controller;

use AppBundle\Entity\User;
use ForumBundle\Entity\Commentaire;
use ForumBundle\Entity\Likes;
use ForumBundle\Entity\Sujet;
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

/**
 * Like controller.
 *
 * @Route("likes")
 */
class LikesController extends Controller
{
    /**
     * Lists all like entities.
     *
     * @Route("/", name="likes_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $likes = $em->getRepository('ForumBundle:Likes')->findAll();

        return $this->render('likes/index.html.twig', array(
            'likes' => $likes,
        ));
    }

    /**
     * Creates a new like entity.
     *
     * @Route("/new", name="likes_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request, Sujet $sujet)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $like = new Likes();
        $like->setSujet($sujet);
        $like->setType("sujet");
        $like->setCreateur($user->getId());
        $em = $this->getDoctrine()->getManager();
        $sujet->setScore($sujet->getScore()+1);
        $em->persist($like);
        $em->persist($sujet);
        $aa = $em->getRepository(Likes::class)->findBy(array("createur"=>$user->getId()));
        $em->flush();
        return $this->redirectToRoute('sujet_index', array('aa' => $aa));
    }

    public function newMobileAction($id,$user)
    {
        $user=$this->getDoctrine()->getRepository(User::class)->find($user);
        $sujet=$this->getDoctrine()->getRepository(Sujet::class)->find($id);
        $like = new Likes();
        $like->setSujet($sujet);
        $like->setType("sujet");
        $like->setCreateur($user->getId());
        $em = $this->getDoctrine()->getManager();
        $sujet->setScore($sujet->getScore()+1);
        $em->persist($like);
        $em->persist($sujet);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($like);
        return new JsonResponse($formatted);
    }

    public function newCommMobileAction($id,$user)
    {
        $user=$this->getDoctrine()->getRepository(User::class)->find($user);
        $comm=$this->getDoctrine()->getRepository(Commentaire::class)->find($id);
        $like = new Likes();
        $like->setCommentaire($comm);
        $like->setType("commentaire");
        $like->setCreateur($user->getId());
        $em = $this->getDoctrine()->getManager();
        $comm->setScore($comm->getScore()+1);
        $em->persist($like);
        $em->persist($comm);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($like);
        return new JsonResponse($formatted);
    }

    public function getallAction()
    {
        $like = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Likes')->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($like);
        return new JsonResponse($formatted);
    }

    public function deleteMobileAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Likes::class)->find($id);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aa);
        $en->remove($aa);
        $sujet=$en->getRepository(Sujet::class)->find($aa->getSujet()->getId());
        $sujet->setScore($sujet->getScore()-1);
        $en->persist($sujet);
        $en->flush();
        return new JsonResponse($formatted);
    }

    public function deleteMobileCommAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Likes::class)->find($id);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aa);
        $en->remove($aa);
        $comm=$en->getRepository(Commentaire::class)->find($aa->getCommentaire()->getId());
        $comm->setScore($comm->getScore()-1);
        $en->persist($comm);
        $en->flush();
        return new JsonResponse($formatted);
    }

    public function newshowAction(Request $request, Sujet $sujet)
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $like = new Likes();
        $like->setSujet($sujet);
        $like->setType("sujet");
        $like->setCreateur($user->getId());
        $em = $this->getDoctrine()->getManager();
        $sujet->setScore($sujet->getScore()+1);
        $em->persist($like);
        $em->persist($sujet);
        $id = $this->get('session')->get('id');
        $aa = $em->getRepository(Likes::class)->findBy(array("createur"=>$user->getId()));
        $em->flush();
        return $this->redirectToRoute('sujet_show',["id"=>$id]);

    }

    /**
     * Finds and displays a like entity.
     *
     * @Route("/{id}", name="likes_show")
     * @Method("GET")
     */
    public function showAction(Likes $like)
    {
        $deleteForm = $this->createDeleteForm($like);

        return $this->render('likes/show.html.twig', array(
            'like' => $like,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing like entity.
     *
     * @Route("/{id}/edit", name="likes_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Likes $like)
    {
        $deleteForm = $this->createDeleteForm($like);
        $editForm = $this->createForm('ForumBundle\Form\LikesType', $like);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('likes_edit', array('id' => $like->getId()));
        }

        return $this->render('likes/edit.html.twig', array(
            'like' => $like,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a like entity.
     *
     * @Route("/{id}", name="likes_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Likes $like)
    {
        $form = $this->createDeleteForm($like);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($like);
            $em->flush();
        }

        return $this->redirectToRoute('likes_index');
    }

    public function supprimerAction($id)
    {

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $en=$this->getDoctrine()->getManager();
        $aa2 = $en->getRepository(Sujet::class)->find($id);
        $aa = $en->getRepository(Likes::class)->findOneBy(array("sujet"=>$aa2,"createur"=>$user->getId()));
        $aa2->setScore($aa2->getScore()-1);
        $en->persist($aa2);
        $en->remove($aa);
        $en->flush();
        return $this->redirectToRoute('sujet_index');
    }

    public function supprimershowAction($id)
    {

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $en=$this->getDoctrine()->getManager();
        $aa2 = $en->getRepository(Sujet::class)->find($id);
        $aa = $en->getRepository(Likes::class)->findOneBy(array("sujet"=>$aa2,"createur"=>$user->getId()));
        $aa2->setScore($aa2->getScore()-1);

        $en->persist($aa2);
        $en->remove($aa);
        $en->flush();
        return $this->redirectToRoute('sujet_show',["id"=>$id,"aa"=>$aa2]);
    }
    /**
     * Creates a form to delete a like entity.
     *
     * @param Likes $like The like entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Likes $like)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('likes_delete', array('id' => $like->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
