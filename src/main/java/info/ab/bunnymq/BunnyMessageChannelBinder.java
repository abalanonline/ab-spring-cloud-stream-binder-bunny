/*
 * Copyright 2020 Aleksei Balan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.ab.bunnymq;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;
import org.springframework.context.annotation.Import;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Log4j
@Service
@Import({BunnyProvisioningProvider.class, BunnyMQ.class})
public class BunnyMessageChannelBinder
    extends AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties,
    ProvisioningProvider<ConsumerProperties, ProducerProperties>> {

  final BunnyMQ bunnyMQ;

  @Autowired
  public BunnyMessageChannelBinder(BunnyProvisioningProvider provisioningProvider, BunnyMQ bunnyMQ) {
    super(true, new String[0], provisioningProvider);
    this.bunnyMQ = bunnyMQ;
  }

  @Override
  protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ProducerProperties producerProperties, MessageChannel errorChannel) {
    return bunnyMQ;
  }

  @Override
  protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ConsumerProperties properties) {
    return bunnyMQ;
  }
}
