<?php

namespace EnseignantBundle\Form;

use Doctrine\ORM\EntityRepository;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class NotesType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $classe = $options['classe'];
        $builder->add('Type', ChoiceType::class, [
            'choices'  => [
                'CC' => 'CC',
                'Devoir de controle' => 'Devoir de controle',
                'Devoir de synthese' => 'Devoir de synthese',
            ],
        ])->add('valeur')->add('id_trimestre', ChoiceType::class, [
            'choices'  => [
                '1' => 1,
                '2' => 2,
                '3' => 3,
            ]
        ])->add('matiere')->add('eleve', EntityType::class, [
            'class' => 'AppBundle:User', 'query_builder' => function (EntityRepository $er) use ($classe) {
                return $er->createQueryBuilder('u')
                    ->where('u.roles = :role')->andWhere('u.classedeseleves= :classe')
                    ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')->setParameter('classe', $classe);
            }]);
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EnseignantBundle\Entity\Notes'
        ));
        $resolver->setRequired(['classe',]);
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'enseignantbundle_notes';
    }


}
