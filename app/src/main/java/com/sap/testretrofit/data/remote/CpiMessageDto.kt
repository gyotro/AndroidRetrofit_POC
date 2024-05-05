package com.sap.testretrofit.data.remote

import com.google.gson.annotations.SerializedName

data class MessageProcessingLogResponseDto(
    @SerializedName("d") val d: D
)

data class D(
    @SerializedName("results") val results: List<MessageProcessingLog>
)

data class MessageProcessingLog(
    @SerializedName("__metadata") val metadata: Metadata,
    @SerializedName("MessageGuid") val messageGuid: String,
    @SerializedName("CorrelationId") val correlationId: String,
    @SerializedName("ApplicationMessageId") val applicationMessageId: String?,
    @SerializedName("ApplicationMessageType") val applicationMessageType: String?,
    @SerializedName("LogStart") val logStart: String,
    @SerializedName("LogEnd") val logEnd: String,
    @SerializedName("Sender") val sender: String?,
    @SerializedName("Receiver") val receiver: String?,
    @SerializedName("IntegrationFlowName") val integrationFlowName: String,
    @SerializedName("Status") val status: String,
    @SerializedName("AlternateWebLink") val alternateWebLink: String,
    @SerializedName("IntegrationArtifact") val integrationArtifact: IntegrationArtifact,
    @SerializedName("LogLevel") val logLevel: String,
    @SerializedName("CustomStatus") val customStatus: String,
    @SerializedName("ArchivingStatus") val archivingStatus: String,
    @SerializedName("ArchivingSenderChannelMessages") val archivingSenderChannelMessages: Boolean,
    @SerializedName("ArchivingReceiverChannelMessages") val archivingReceiverChannelMessages: Boolean,
    @SerializedName("ArchivingLogAttachments") val archivingLogAttachments: Boolean,
    @SerializedName("ArchivingPersistedMessages") val archivingPersistedMessages: Boolean,
    @SerializedName("TransactionId") val transactionId: String,
    @SerializedName("PreviousComponentName") val previousComponentName: String,
    @SerializedName("LocalComponentName") val localComponentName: String,
    @SerializedName("OriginComponentName") val originComponentName: String,
    @SerializedName("CustomHeaderProperties") val customHeaderProperties: CustomHeaderProperties,
    @SerializedName("MessageStoreEntries") val messageStoreEntries: MessageStoreEntries,
    @SerializedName("ErrorInformation") val errorInformation: ErrorInformation,
    @SerializedName("AdapterAttributes") val adapterAttributes: AdapterAttributes,
    @SerializedName("Attachments") val attachments: Attachments,
    @SerializedName("Runs") val runs: Runs
)


data class Metadata(
    @SerializedName("id") val id: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("type") val type: String
)


data class IntegrationArtifact(
    @SerializedName("__metadata") val metadata: Metadata,
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Type") val type: String,
    @SerializedName("PackageId") val packageId: String,
    @SerializedName("PackageName") val packageName: String
)


data class CustomHeaderProperties(
    @SerializedName("__deferred") val deferred: Deferred
)


data class MessageStoreEntries(
    @SerializedName("__deferred") val deferred: Deferred
)


data class ErrorInformation(
    @SerializedName("__deferred") val deferred: Deferred
)


data class AdapterAttributes(
    @SerializedName("__deferred") val deferred: Deferred
)


data class Attachments(
    @SerializedName("__deferred") val deferred: Deferred
)


data class Runs(
    @SerializedName("__deferred") val deferred: Deferred
)


data class Deferred(
    @SerializedName("uri") val uri: String
)
