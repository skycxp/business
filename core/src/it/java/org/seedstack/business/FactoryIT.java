/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.fixtures.factory.MyFactoryAggregate;
import org.seedstack.business.fixtures.identity.MyAggregate;
import org.seedstack.business.fixtures.identity.MyAggregateFactory;
import org.seedstack.business.fixtures.identity.MyAggregateFactoryDefault;
import org.seedstack.business.fixtures.qualifier.domain.DomPolicyImpl1;
import org.seedstack.business.fixtures.qualifier.domain.DomServiceImpl1;
import org.seedstack.business.fixtures.qualifier.domain.DomServiceImpl2;
import org.seedstack.business.fixtures.qualifier.domain.MyDomainEvent;
import org.seedstack.business.fixtures.qualifier.domain.MyDomainPolicy;
import org.seedstack.business.fixtures.qualifier.domain.MyDomainService;
import org.seedstack.business.fixtures.qualifier.domain.MyValueObject;
import org.seedstack.business.internal.FactoryInternal;
import org.seedstack.seed.it.SeedITRunner;

import javax.inject.Inject;
import javax.inject.Named;

@RunWith(SeedITRunner.class)
public class FactoryIT {

    @Inject
    Factory<MyValueObject> myValueObjectFactory;

    @Inject
    Factory<MyDomainEvent> myDomainEventFactory;

    @Inject
    Factory<MyDomainService> myDomainServiceFactory;

    @Inject
    @Named("2")
    Factory<MyDomainService> myQualifiedDomainServiceFactory;

    @Inject
    Factory<MyDomainPolicy<String>> myDomainPolicyFactory;

    @Inject
    Factory<MyFactoryAggregate> myAggregateAutoFactory;

    @Inject
    Factory<MyAggregate> myAggregateGenericFactory;

    @Inject
    MyAggregateFactory myAggregateFactory;


    @Test
    public void factory_injection_test() {
        Assertions.assertThat(myValueObjectFactory).isNotNull();
        Assertions.assertThat(myValueObjectFactory).isInstanceOf(FactoryInternal.class);

        Assertions.assertThat(myDomainEventFactory).isNotNull();
        Assertions.assertThat(myDomainEventFactory).isInstanceOf(FactoryInternal.class);

        Assertions.assertThat(myDomainServiceFactory).isNotNull();
        Assertions.assertThat(myDomainServiceFactory).isInstanceOf(FactoryInternal.class);

        Assertions.assertThat(myDomainPolicyFactory).isNotNull();
        Assertions.assertThat(myDomainPolicyFactory).isInstanceOf(FactoryInternal.class);

        Assertions.assertThat(myAggregateAutoFactory).isNotNull();
        Assertions.assertThat(myAggregateAutoFactory).isInstanceOf(FactoryInternal.class);

        Assertions.assertThat(myAggregateGenericFactory).isNotNull();
        Assertions.assertThat(myAggregateGenericFactory).isInstanceOf(MyAggregateFactoryDefault.class);

        Assertions.assertThat(myAggregateFactory).isNotNull();
        Assertions.assertThat(myAggregateFactory).isInstanceOf(MyAggregateFactoryDefault.class);
    }

    @Test
    public void auto_factory_should_handle_value_object() {
        MyValueObject myValueObject = myValueObjectFactory.create("John", "Doe");
        Assertions.assertThat(myValueObject).isNotNull();
        Assertions.assertThat(myValueObject.getFirstName()).isEqualTo("John");
        Assertions.assertThat(myValueObject.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void auto_factory_should_handle_domain_event() {
        MyDomainEvent myDomainEvent = myDomainEventFactory.create("something happened");
        Assertions.assertThat(myDomainEvent).isNotNull();
        Assertions.assertThat(myDomainEvent.getCause()).isEqualTo("something happened");
    }

    @Test
    public void auto_factory_should_handle_domain_service() {
        MyDomainService myDomainService = myDomainServiceFactory.create();
        Assertions.assertThat(myDomainService).isNotNull();
        Assertions.assertThat(myDomainService).isInstanceOf(DomServiceImpl1.class);


        MyDomainService myDomainService2 = myQualifiedDomainServiceFactory.create("coucou");
        Assertions.assertThat(myDomainService2).isNotNull();
        Assertions.assertThat(myDomainService2).isInstanceOf(DomServiceImpl2.class);
        Assertions.assertThat(((DomServiceImpl2) myDomainService2).getMessage()).isEqualTo("coucou");
    }

    @Test
    public void auto_factory_should_handle_domain_policy() {
        MyDomainPolicy<String> myDomainPolicy = myDomainPolicyFactory.create();
        Assertions.assertThat(myDomainPolicy).isNotNull();
        Assertions.assertThat(myDomainPolicy).isInstanceOf(DomPolicyImpl1.class);
    }
}
