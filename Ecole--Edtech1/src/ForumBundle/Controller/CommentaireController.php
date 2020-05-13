<?php

namespace ForumBundle\Controller;

use AppBundle\Entity\User;
use ForumBundle\Entity\Commentaire;
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
 * Commentaire controller.
 *
 * @Route("commentaire")
 */
class CommentaireController extends Controller
{
    /**
     * Lists all commentaire entities.
     *
     * @Route("/", name="commentaire_index")
     * @Method("GET")
     */
    public function indexAction(Sujet $sujet)
    {
        $this->get('session')->set('id', $sujet->getId());
        $em = $this->getDoctrine()->getManager();

        $commentaires = $em->getRepository('ForumBundle:Commentaire')->findBy(array("sujet"=>$sujet));

        return $this->render('commentaire/index.html.twig', array('sujet'=>$sujet,
            'commentaires' => $commentaires,
        ));
    }

    /**
     * Creates a new commentaire entity.
     *
     * @Route("/new", name="commentaire_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $commentaire = new Commentaire();
        $form = $this->createForm('ForumBundle\Form\CommentaireType', $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($commentaire);
            $form->initialize();
            $em->flush();
            $id = $this->get('session')->get('id');
            return $this->redirectToRoute('sujet_show',["id"=>$id]);
        }
        return $this->render('commentaire/new.html.twig', array(
            'commentaire' => $commentaire,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a commentaire entity.
     *
     * @Route("/{id}", name="commentaire_show")
     * @Method("GET")
     */
    public function showAction(Commentaire $commentaire)
    {
        $deleteForm = $this->createDeleteForm($commentaire);

        return $this->render('commentaire/show.html.twig', array(
            'commentaire' => $commentaire,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing commentaire entity.
     *
     * @Route("/{id}/edit", name="commentaire_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Commentaire $commentaire)
    {
        $deleteForm = $this->createDeleteForm($commentaire);
        $editForm = $this->createForm('ForumBundle\Form\CommentaireType', $commentaire);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $id = $this->get('session')->get('id');
            return $this->redirectToRoute('sujet_show',["id"=>$id]);
        }

        return $this->render('commentaire/edit.html.twig', array(
            'commentaire' => $commentaire,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a commentaire entity.
     *
     * @Route("/{id}", name="commentaire_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Commentaire $commentaire)
    {
        $form = $this->createDeleteForm($commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($commentaire);
            $em->flush();
        }

        return $this->redirectToRoute('commentaire_index');
    }

    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Commentaire::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        $id = $this->get('session')->get('id');
        return $this->redirectToRoute('sujet_show',["id"=>$id]);
    }

    public function supprimerBAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Commentaire::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        $id = $this->get('session')->get('id');
        return $this->redirect('http://127.0.0.1:8000/backF/commentaire/'.$id);
    }


    public function getallAction()
    {
        $c = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Commentaire')->findAll();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function newMobileAction($texte,$sujet,$createur_id)
    {
        $em = $this->getDoctrine()->getManager();
        $c = new Commentaire();
        $user=$this->getDoctrine()->getRepository(User::class)->find($createur_id);
        $c->setSujet( $this->getDoctrine()->getManager()->getRepository('ForumBundle:Sujet')->find($sujet));
        $c->setCreateur($user->getId());
        $c->setTexte($texte);
        $c->setDate(new \DateTime('now'));
        $c->setScore(0);
        $em->persist($c);
        $em->flush();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function deleteMobileAction($id)
    {
        $en=$this->getDoctrine()->getManager();
        $aa = $en->getRepository(Commentaire::class)->find($id);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aa);
        $en->remove($aa);
        $en->flush();
        return new JsonResponse($formatted);
    }

    public function modifMobileAction($id,$texte)
    {
        $c = $this->getDoctrine()->getManager()->getRepository('ForumBundle:Commentaire')->find($id);
        $c->setTexte($texte);
        $this->getDoctrine()->getManager()->flush();
        $serializer = new Serializer([new DateTimeNormalizer('yy-m-d H:m:s'), new GetSetMethodNormalizer()], array('jso  n' => new JsonEncoder()));
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }
    /**
     * Creates a form to delete a commentaire entity.
     *
     * @param Commentaire $commentaire The commentaire entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Commentaire $commentaire)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('commentaire_delete', array('id' => $commentaire->getId())))
            ->setMethod('DELETE')
            ->getForm()
            ;
    }
}
