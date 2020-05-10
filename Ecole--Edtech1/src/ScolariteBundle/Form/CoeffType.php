<?php

namespace ScolariteBundle\Form;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CoeffType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('niveau', EntityType::class,['class'=>'ScolariteBundle:Classe',
            'choice_label'=>'libelle', 'multiple'=>false,'expanded'=>false])

        ->add('matiere', EntityType::class,['class'=>'ScolariteBundle:Matiere',
            'choice_label'=>'nom','query_builder' => function(EntityRepository $repo1) {
                return $repo1->createQueryBuilder('m');
            },'multiple'=>false,'expanded'=>true])
        ->add('valeur');
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ScolariteBundle\Entity\Coeff'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'scolaritebundle_coeff';
    }


}
