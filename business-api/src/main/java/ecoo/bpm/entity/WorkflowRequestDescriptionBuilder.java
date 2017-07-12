package ecoo.bpm.entity;

import ecoo.data.Company;
import ecoo.data.CompanyStatus;
import ecoo.data.ShipmentStatus;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class WorkflowRequestDescriptionBuilder {
    private WorkflowRequest workflowRequest;

    private WorkflowRequestDescriptionBuilder() {
    }

    public static WorkflowRequestDescriptionBuilder aDescription() {
        return new WorkflowRequestDescriptionBuilder();
    }

    public WorkflowRequestDescriptionBuilder withWorkflowRequest(WorkflowRequest workflowRequest) {
        this.workflowRequest = workflowRequest;
        return this;
    }

    public String build() {
        switch (workflowRequest.getType()) {
            case UserRegistration:
                final RegisterUserAccountRequest registerUserAccountRequest = (RegisterUserAccountRequest) workflowRequest;
                final Company newCompany = ((RegisterUserAccountRequest) workflowRequest).getCompany();
                if (newCompany.isInStatus(CompanyStatus.PendingDocumentation)) {
                    return String.format("Proof of company documents are required for company %s."
                            , registerUserAccountRequest.getCompany().getName());

                } else if (newCompany.isInStatus(CompanyStatus.PendingApproval)) {
                    return String.format("User %s is requesting new company %s."
                            , registerUserAccountRequest.getUser().getDisplayName()
                            , registerUserAccountRequest.getCompany().getName());

                } else {
                    return String.format("User %s from %s is requesting membership to %s."
                            , registerUserAccountRequest.getUser().getDisplayName()
                            , registerUserAccountRequest.getCompany().getName()
                            , registerUserAccountRequest.getChamber().getName());
                }
            case NewShipmentRequest:
                final NewShipmentRequest newShipmentRequest = (NewShipmentRequest) workflowRequest;
                if (newShipmentRequest.getShipment().isInStatus(ShipmentStatus.SubmittedAndPendingChamberApproval)) {
                    final Company company = newShipmentRequest.getRequestingUser().getCompany();
                    if (company == null) {
                        return String.format("%s is requesting approval for shipment %s with exporter reference %s."
                                , newShipmentRequest.getRequestingUser().getDisplayName()
                                , newShipmentRequest.getShipment().getPrimaryId()
                                , newShipmentRequest.getShipment().getExporterReference());
                    } else {
                        return String.format("%s from %s is requesting approval for shipment %s with exporter reference %s."
                                , newShipmentRequest.getRequestingUser().getDisplayName()
                                , company.getName()
                                , newShipmentRequest.getShipment().getPrimaryId()
                                , newShipmentRequest.getShipment().getExporterReference());
                    }
                } else {
                    return String.format("More work is required for shipment %s with exporter reference %s."
                            , newShipmentRequest.getShipment().getPrimaryId()
                            , newShipmentRequest.getShipment().getExporterReference());
                }
            default:
                throw new UnsupportedOperationException(String.format("Process type %s not supported.", workflowRequest.getType()));
        }
    }
}
