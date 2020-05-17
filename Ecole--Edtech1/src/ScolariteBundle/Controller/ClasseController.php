<?php

namespace ScolariteBundle\Controller;

use ScolariteBundle\Entity\Classe;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Security;
use Swift_Mailer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\JsonSerializableNormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\JsonResponse;
/**
 * Classe controller.
 *
 * @Route("classe")
 */
class ClasseController extends Controller
{

    public function sendMailAction($idEns)
    {
        //$user = $this->container->get('security.token_storage')->getToken()->getUser();
        //$user->getNom();
        $ens = $this->getDoctrine()->getManager()
            ->getRepository('AppBundle:User')
            ->findOneById($idEns);
        $message = \Swift_Message::newInstance()
            ->setSubject('Hello Email')
            ->setFrom('bennouri.olfa@gmail.com')
            ->setTo($ens->getEmail())
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

/*    API             */

    public function newClassApiAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $cl = new Classe();
        $cl->setNiveau($request->get('niveau'));
        $cl->setLibelle($request->get('libelle'));
        $cl->setCapacite($request->get('capacite'));
        $em->persist($cl);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($cl);
        return new JsonResponse($formatted);
    }

    public function allpAction()
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->findAll();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants'));
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

    public function findClasseAction($libelle)
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->findOneByLibelle($libelle);
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants','notes','moyenne'));
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

    public function findClasseElvAction($ide)
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->GetClasseEleve2($ide);
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants','notes','moyenne'));
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


    public function findClasseElv1Action($ide)
    {
        $tasks = $this->getDoctrine()->getManager()
            ->getRepository('ScolariteBundle:Classe')
            ->GetUserParClasseElv($ide);
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants','notes','moyenne','eleves','enseignants',
            'enfants','parent','email','date_embauche','moyennes','notes','absenceseleve',
            'dateEmbauche','groupNames','groups','superAdmin','location','lastLogin',
            'emailCanonical','absences','roles','dateInscription','usernameCanonical','password',
            'sanctions','credentialsNonExpired','confirmationToken','accountNonExpired',
            'passwordRequestedAt','sanctionseleve','noteseleve'
        ));
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


    public function supprimerClasseApiAction($id)
    {
        $c=$this->getDoctrine()->getRepository(Classe::class)->find($id);
        $en=$this->getDoctrine()->getManager();
        $en->remove($c);
        $en->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($c, 'json');
        $formatted1 = $serializer->normalize($c);
        return new JsonResponse($formatted1);

    }

    public function editClasseApiAction(Request $request,$id)
    {
        $c=$this->getDoctrine()->getRepository(Classe::class)->find($id);

        $en=$this->getDoctrine()->getManager();
        $c->setNiveau($request->get('niveau'));
        $c->setLibelle($request->get('libelle'));
        $c->setCapacite($request->get('capacite'));
        $en->flush();
        $encoder = array (new JsonEncoder());
        $normalizer = array(new ObjectNormalizer());
        $normalizer[0]->setIgnoredAttributes(array('eleves','enseignants'));
        // $normalizer->setIgnoredAttributes(['enseignants']);
        $normalizer[0]->setCircularReferenceLimit(1);
        $normalizer[0]->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });

        $serializer = new Serializer($normalizer, $encoder);

        $formatted = $serializer->serialize($c, 'json');
        $formatted1 = $serializer->normalize($c);
        return new JsonResponse($formatted1);

    }
	
	    //Travail de Selim: récupère la liste des classes d'un niveau
    public function getClasseParNiveauAction($niveau)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT * FROM `classe` WHERE niveau=?";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $niveau);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }

//Travail de Selim: récupère une classe par son identifiant
    public function getNiveauClasseParIDAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $sql="SELECT * FROM `classe` WHERE id=?";

        $statement = $em->getConnection()->prepare($sql);

        $statement->bindValue(1, $id);
        $statement->execute();

        $result = $statement->fetchAll();

        $encoder = array (new JsonEncoder());
        $normalizers = array(new ObjectNormalizer());

        $normalizers[0]->setCircularReferenceLimit(1);
        $serializer = new Serializer($normalizers, $encoder);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }
}
