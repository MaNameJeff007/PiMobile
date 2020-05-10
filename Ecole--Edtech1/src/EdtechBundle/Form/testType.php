<?php

namespace EdtechBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use  Symfony\Component\Validator\Constraints as Assert;

class testType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('question')->add('reponse')->add('typeIntell'
       , IntegerType::class, array(

        'constraints' => array(
            new Assert\NotBlank(),
            new Assert\Type('integer'),
            new Assert\Regex(array(
                    'pattern' => '/^[0-9]\d*$/',
                    'message' => 'Please use only positive numbers.'
                )
            ),
            new Assert\Length(array('max' => 4))
        )));
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'EdtechBundle\Entity\test'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'edtechbundle_test';
    }


}
