<?php

namespace ScolariteBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use ScolariteBundle\Entity\Moyennesgenerales;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Request;


/**
 * Matiere controller.
 *
 * @Route("moyennesgenerales")
 */
class MoyennesgeneralesController extends Controller
{
    public function StatMT2Action($cl,$tr)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $nb=$em->getNumberEleveParClasse($cl,$tr);
        $c = $em->StatMT2R($cl, $tr,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMT1Action($cl,$tr)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $up= $this->getDoctrine()->getManager();
        $c = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->selectMG();
        foreach ($c as $row)
        {

            $eleve=$row['eleve'];
            $moy=$row['moy'];
            $classe=$row['classe'];

            $ch = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->findOneByEleve($eleve);

            if (!$ch)
            {
                $m = new Moyennesgenerales();
                $m->setClasse($classe);
                $m->setEleve($eleve);
                $m->setMoyG($moy);

                $up->persist($m);
                $up->flush();
            }
            else {
                $ch->setClasse($classe);
                $ch->setMoyG($moy);
                $up->flush();
            }
        }


        $nb=$em->getNumberEleveParClasse($cl,$tr);
        $c = $em->StatMT1R($cl, $tr,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMT3Action($cl,$tr)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $nb=$em->getNumberEleveParClasse($cl,$tr);
        $c = $em->StatMT3R($cl, $tr,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMGC1Action($cl)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $up= $this->getDoctrine()->getManager();
        $c = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->selectMG();
        foreach ($c as $row)
        {

            $eleve=$row['eleve'];
            $moy=$row['moy'];
            $classe=$row['classe'];

            $ch = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->findOneByEleve($eleve);

            if (!$ch)
            {
                $m = new Moyennesgenerales();
                $m->setClasse($classe);
                $m->setEleve($eleve);
                $m->setMoyG($moy);

                $up->persist($m);
                $up->flush();
            }
            else {
                $ch->setClasse($classe);
                $ch->setMoyG($moy);
                $up->flush();
            }
        }

        $nb=$em->countMoyennesgenerales($cl);
        $c = $em->StatMGC1R($cl,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMGC2Action($cl)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $nb=$em->countMoyennesgenerales($cl);
        $c = $em->StatMGC2R($cl,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMGC3Action($cl)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $nb=$em->countMoyennesgenerales($cl);
        $c = $em->StatMGC3R($cl,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function StatMGC4Action($cl)
    {
        $em=$this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class);
        $nb=$em->countMoyennesgenerales($cl);
        $c = $em->StatMGC4R($cl,$nb);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }

    public function updateMGAction()
    {
        $em= $this->getDoctrine()->getManager();
        $c = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->selectMG();
       foreach ($c as $row)
        {

            $eleve=$row['eleve'];
            $moy=$row['moy'];
            $classe=$row['classe'];

            $ch = $this->getDoctrine()->getManager()->getRepository(Moyennesgenerales::class)->findOneByEleve($eleve);

            if (!$ch)
            {
                $m = new Moyennesgenerales();
                $m->setClasse($classe);
                $m->setEleve($eleve);
                $m->setMoyG($moy);

                $em->persist($m);
                $em->flush();
            }
            else {
                $ch->setClasse($classe);
                $ch->setMoyG($moy);
                $em->flush();
            }
        }

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($c);
        return new JsonResponse($formatted);
    }
}
