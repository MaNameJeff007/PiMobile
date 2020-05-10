<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Classe;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Security;
use Swift_Mailer;
/**
 * Classe controller.
 *
 * @Route("classe")
 */
class ClasseController extends Controller
{

    public function sendMailAction()
    {
        $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $user->getNom();
        $message = \Swift_Message::newInstance()
            ->setSubject('Hello Email')
            ->setFrom('olfa.bennouri@esprit.tn')
            ->setTo('bennouri.olfa@gmail.com')
            ->setBody('Here is the message itself')

            /*
             * If you also want to include a plaintext version of the message
            ->addPart(
                $this->renderView(
                    'Emails/registration.txt.twig',
                    array('name' => $name)
                ),
                'text/plain'
            )
            */
        ;
        $this->get('mailer')->send($message);

       return $this->render('default/index.html.twig', [
           'base_dir' => realpath($this->getParameter('kernel.project_dir')).DIRECTORY_SEPARATOR,
       ]);
       /* $user = $this->container->get('security.token_storage')->getToken()->getUser();
        $user->getEmail();
       $transport = (new \Swift_SmtpTransport('localhost', 25));

        $mailer = new \Swift_Mailer($transport);
        $message = (new \Swift_Message())

            // Give the message a subject
            ->setSubject('test mailer')

            // Set the From address with an associative array
            ->setFrom($user)

            // Set the To addresses with an associative array (setTo/setCc/setBcc)
            ->setTo(['bennouri.olfa@gmail.com' => 'A name'])

            // Give it a body
            ->setBody('Here is the message itself')

            // And optionally an alternative body
            ->addPart('<q>Here is the message itself</q>', 'text/html')

            // Optionally add any attachments
        ;
        $result = $mailer->send($message);*/
    }
    /**
     * Lists all classe entities.
     *
     * @Route("/", name="classe_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $classes = $em->getRepository('ScolariteBundle:Classe')->findAll();

        return $this->render('classe/index.html.twig', array(
            'classes' => $classes,
        ));
    }

    /**
     * Creates a new classe entity.
     *
     * @Route("/new", name="classe_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $classe = new Classe();
        $form = $this->createForm('ScolariteBundle\Form\ClasseType', $classe);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $cn = $classe->getLibelle();

            $em1 = $this->getDoctrine()->getManager()->getRepository(Classe::class);
            $check = $em1->findCasse($cn);

            if (!count($check)) {

                $em = $this->getDoctrine()->getManager();
                $em->persist($classe);
                $em->flush();

                return $this->redirectToRoute('classe_index', array('id' => $classe->getId()));
            } else {
                $this->addFlash(
                    'errorC ', 'Cette classe existe deja !.'
                );
                return $this->render('default/errors.html.twig');

            }

        }

        return $this->render('classe/new.html.twig', array(
            'classe' => $classe,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a classe entity.
     *
     * @Route("/{id}", name="classe_show")
     * @Method("GET")
     */
    public function showAction(Classe $classe)
    {
        $deleteForm = $this->createDeleteForm($classe);

        return $this->render('classe/show.html.twig', array(
            'classe' => $classe,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing classe entity.
     *
     * @Route("/{id}/edit", name="classe_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Classe $classe)
    {
        $deleteForm = $this->createDeleteForm($classe);
        $editForm = $this->createForm('ScolariteBundle\Form\ClasseType', $classe);
        $editForm->handleRequest($request);


        if ($editForm->isSubmitted() && $editForm->isValid()) {

            $cn = $classe->getLibelle();

            $em1 = $this->getDoctrine()->getManager()->getRepository(Classe::class);
            $check = $em1->findCasse($cn);

            if (!count($check)) {

                $em = $this->getDoctrine()->getManager();
                $em->flush();

                return $this->redirectToRoute('classe_edit', array('id' => $classe->getId()));
            } else {
                $this->addFlash(
                    'errorC ', 'Cette classe existe deja !.'
                );
                return $this->render('default/errors.html.twig');

            }

        }

        return $this->render('classe/edit.html.twig', array(
            'classe' => $classe,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a classe entity.
     *
     * @Route("/{id}", name="classe_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Classe $classe)
    {
        $form = $this->createDeleteForm($classe);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($classe);
            $em->flush();
        }

        return $this->redirectToRoute('classe_index');
    }

    /**
     * Creates a form to delete a classe entity.
     *
     * @param Classe $classe The classe entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Classe $classe)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('classe_delete', array('id' => $classe->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

    public function supprimerAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Classe::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();

        return $this->redirectToRoute('classe_index');
    }

    public function FindEnfantsAction()
    {

        $user = $this->container->get('security.token_storage')->getToken()->getUser();
       $parent = $user->getId();

        $em=$this->getDoctrine()->getManager()->getRepository(Classe::class);
        $n=$em->GetClasseEleve($parent);
        return $this->render('seance/empEleve.html.twig',array('son'=>$n));
    }


}
