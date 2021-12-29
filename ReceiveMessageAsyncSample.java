package com.proview.epmm.api.tests;

/**
 * Sample demonstrates how to receive an {@link ServiceBusReceivedMessage} from an Azure Service Bus Queue using
 * connection string.
 */
public class ReceiveMessageAsyncSample {
    /**
     * Main method to invoke this demo on how to receive an {@link ServiceBusMessage} from an Azure Service Bus
     * Queue
     *
     * @param args Unused arguments to the program.
     */
    public static void main(String[] args) {

        // The connection string value can be obtained by:
        // 1. Going to your Service Bus namespace in Azure Portal.
        // 2. Go to "Shared access policies"
        // 3. Copy the connection string for the "RootManageSharedAccessKey" policy.
       /* String connectionString = "Endpoint={fully-qualified-namespace};SharedAccessKeyName={policy-name};"
            + "SharedAccessKey={key}";*/
        
        String connectionString = "Endpoint=sb://pvcdeveventbus.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=z6bwA23ZDH++3AJm2bRYjwU9mhIq6BuvVFtKodQ+ggA=";

        // Create a receiver.
        // "<<fully-qualified-namespace>>" will look similar to "{your-namespace}.servicebus.windows.net"
        // "<<queue-name>>" will be the name of the Service Bus queue instance you created inside the Service Bus namespace.
        
		/*
		 * ServiceBusReceiverClient receiverClient = new ServiceBusClientBuilder()
		 * .connectionString(connectionString) .receiver()
		 * .topicName("pv-event-qa-topic").subscriptionName(
		 * "pv-event-qa-topic-subscription") .buildClient();
		 * 
		 * final IterableStream<ServiceBusReceivedMessage> receivedMessages =
		 * receiverClient.receive(5);
		 * 
		 * receivedMessages.stream().forEach(message -> {
		 * System.out.println("Received Message Id:" + message.getMessageId());
		 * System.out.println("Received Message:" + new String(message.getBody())); });
		 * 
		 * // Close the receiver. receiverClient.close();
		 */
    }
}