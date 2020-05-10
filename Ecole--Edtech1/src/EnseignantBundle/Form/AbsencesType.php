<?php

namespace EnseignantBundle\Form;

use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AbsencesType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $classe = $options['classe'];
        $builder->add('date_abs',  DateType::class, array('widget' => 'choice',
            'years' => range(date('Y'), date('Y')),
           ))->add('justification')->add('heuredebut')->add('heurefin')->add('eleve', EntityType::class, [
            'class' => 'AppBundle:User', 'query_builder' => function (EntityRepository $er) use ($classe) {
                return $er->createQueryBuilder('u')
                    ->where('u.roles = :role')
                    ->andWhere('u.classedeseleves= :classe')
                    ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')->setParameter('classe', $classe);
            }]);
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EnseignantBundle\Entity\Absences'
        ));
        $resolver->setRequired(['classe',]);
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'enseignantbundle_absences';
    }


}
