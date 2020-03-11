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

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

//@Service // imported - no annotation
public class BunnyMQ extends MessageProducerSupport implements MessageHandler, Runnable, MessageChannel {

  BlockingQueue<Message<?>> messageQueue = new LinkedBlockingDeque<>();

  public BunnyMQ() {
    setOutputChannel(this); // set to mock before outputChannel is configured
    new Thread(this, BunnyMQ.class.getSimpleName()).start(); // start BunnyMQ!
  }

  @Override
  public void run() { // core of the Bunny Message Queue
    while (true) {
      try {
        sendMessage(messageQueue.take());
      } catch (InterruptedException e) {
        break; // exiting
      }
    }
  }

  @Override
  public void handleMessage(Message<?> message) {
    messageQueue.add(message);
  }

  @Override // mock MessageChannel
  public boolean send(Message<?> message) {
    return false;
  }

  @Override // mock MessageChannel
  public boolean send(Message<?> message, long l) {
    return false;
  }
}
