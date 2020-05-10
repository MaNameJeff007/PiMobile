<?php

namespace ScolariteBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Doctrine\ORM\EntityRepository;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;


class SeanceType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('jour',ChoiceType::class, [
        'choices'  => [
            'Lundi' => 'lundi',
            'Mardi' => 'mardi',
            'Mercredi' => 'mercredi',
            'Jeudi' => 'jeudi',
            'Vendredi' => 'vendredi',
            'Samedi' => 'samedi',
        ],])
            ->add('hdeb',ChoiceType::class, [
                'choices'  => [
                    '8:00:00' => '08:00:00',
                    '10:15:00' => '10:15:00',
                    '12:00:00' => '12:00:00',
                    '13:00:00' => '13:00:00',
                    '15:15:00' => '15:15:00',
                ],])



            ->add('enseignant',EntityType::class,['class'=>'AppBundle:User','choice_label'=>'nom',
                'query_builder' => function (EntityRepository $repo) {
                    $xx = "a:1:{i:0;s:15:\"ROLE_ENSEIGNANT\";}";
                    return $repo->createQueryBuilder('c')->andWhere('c.roles = :xx')->setParameter('xx', $xx);
                },

                 'multiple'=>false,'expanded'=>true])
            ->add('classe',EntityType::class,['class'=>'ScolariteBundle:Classe',
        'choice_label'=>'libelle', 'multiple'=>false,'expanded'=>true])
            ->add('salle',EntityType::class,['class'=>'ScolariteBundle:Salle',
                'choice_label'=>'libelle', 'multiple'=>false,'expanded'=>true])
            ->add('matiere',EntityType::class,['class'=>'ScolariteBundle:Matiere',
                'choice_label'=>'nom', 'multiple'=>false,'expanded'=>true]);
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ScolariteBundle\Entity\Seance'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'scolaritebundle_seance';
    }


}
