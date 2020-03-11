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

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

//@Service // imported - no annotation
public class BunnyProvisioningProvider
    implements ProvisioningProvider<ConsumerProperties, ProducerProperties>, ProducerDestination, ConsumerDestination {

  @Getter @Setter
  String name;

  @Override
  public String getNameForPartition(int partition) {
    return this.name;
  }

  @Override
  public ProducerDestination provisionProducerDestination(String name, ProducerProperties properties) throws ProvisioningException {
    setName(name);
    return this;
  }

  @Override
  public ConsumerDestination provisionConsumerDestination(String name, String group, ConsumerProperties properties) throws ProvisioningException {
    setName(name);
    return this;
  }
}
