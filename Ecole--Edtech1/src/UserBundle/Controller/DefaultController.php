<?php

namespace UserBundle\Controller;

use AppBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('UserBundle:Default:index.html.twig');
    }

 public function registerAction($nom,$prenom,$email,$username,$role,$code)
    {
        $user=new User();
        $user->setUsername($username);
        $user->setUsernameCanonical($username);
        $user->setEmail($email);
        $user->setEmailCanonical($email);
        $user->setPassword("$2y$13\$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om");
        $user->setNom($nom);
        $user->setPrenom($prenom);
        $user->setRoles(array("roles"=>$role));
        $user->setEnabled(true);
        $date_embauche=new \DateTime("now");
        $date_inscription=new \DateTime("now");
        $user->setDateInscription($date_inscription);
        $user->setDateEmbauche($date_embauche);
        $user->setLastLogin($date_embauche);

        $message = \Swift_Message::newInstance()
            ->setSubject('Confirmation')
            ->setFrom('mohamedyassine.ghadhoune@esprit.tn')
            ->setTo('mohamedyassine.ghadhoune@esprit.tn')
            ->setBody($code);

        $this->get('mailer')->send($message);
        $em = $this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }

    public function loginAction($username)
    {
        $em = $this->getDoctrine()->getManager();


        $req="SELECT * FROM `user` WHERE username=?";
        $statement = $em->getConnection()->prepare($req);

        // Set parameters
        $statement->bindValue(1, $username);
        $statement->execute();
        $result = $statement->fetchAll();

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);
        $formatted = $serializer->normalize($result);
        return new JsonResponse($formatted);
    }    
    
     public function NomAction($username)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $query = $entityManager->createQuery(
            'SELECT u
             FROM AppBundle:User u        
             WHERE u.id = :username'
        )->setParameters(array(
                'username'=>$username)
        );
        $user = $query->getResult();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(0);

        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getId();
        });
        $normalizers = array($normalizer);
        $serializer = new Serializer($normalizers);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }
}
