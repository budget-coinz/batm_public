/*************************************************************************************
 * Copyright (C) 2014-2020 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/
package com.budgetcoinz.batm.server.extensions.shared;

import com.generalbytes.batm.server.extensions.IIdentityPiece;
import com.generalbytes.batm.server.extensions.IPerson;

import java.util.Date;

public class IdentityPieceBc implements IIdentityPiece {

    private final int type;
    private final String phoneNumber;
    private final String emailAddress;
    private final String firstname;
    private final String lastname;
    private final String idCardNumber;
    private final Integer documentType;
    private final Date documentValidTo;
    private final String contactZIP;
    private final String contactCountry;
    private final String contactCountryIso2;
    private final String contactProvince;
    private final String contactCity;
    private final String contactAddress;
    private final String filename;
    private final String mimeType;
    private final byte[] data;
    private final Date dateOfBirth;
    private final String occupation;
    private final String ssn;
    private final Date created;

    IdentityPieceBc(int type, String phoneNumber, String emailAddress, String firstname, String lastname, String idCardNumber, Integer documentType, Date documentValidTo,
                    String contactZIP, String contactCountry, String contactCountryIso2, String contactProvince, String contactCity, String contactAddress, Date dateOfBirth, String occupation, String ssn,
                    String filename, String mimeType, byte[] data, Date created) {
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.firstname = firstname;
        this.lastname = lastname;
        this.idCardNumber = idCardNumber;
        this.documentType = documentType;
        this.documentValidTo = documentValidTo;
        this.contactZIP = contactZIP;
        this.contactCountry = contactCountry;
        this.contactCountryIso2 = contactCountryIso2;
        this.contactProvince = contactProvince;
        this.contactCity = contactCity;
        this.contactAddress = contactAddress;
        this.dateOfBirth = dateOfBirth;
        this.filename = filename;
        this.mimeType = mimeType;
        this.data = data;
        this.occupation = occupation;
        this.ssn = ssn;
        this.created = created;
    }

    public static IdentityPieceBc fromEmailAddress(String emailAddress) {
        return new IdentityPieceBc(TYPE_EMAIL, null, emailAddress, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public static IdentityPieceBc fromSelfie(String mimeType, byte[] data) {
        return new IdentityPieceBc(TYPE_SELFIE, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, mimeType, data, null);
    }

    public static IdentityPieceBc fromCameraImage(String mimeType, byte[] data) {
        return new IdentityPieceBc(TYPE_CAMERA_IMAGE, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, mimeType, data,null);
    }

    public static IdentityPieceBc fromFingerprint(byte[] data) {
        return new IdentityPieceBc(TYPE_FINGERPRINT, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, data,null);
    }

    public static IdentityPieceBc fromIdScan(String mimeType, byte[] data) {
        return new IdentityPieceBc(TYPE_ID_SCAN, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, mimeType, data,null);
    }

    public static IdentityPieceBc fromPhoneNumber(String phoneNumber) {
        return new IdentityPieceBc(TYPE_CELLPHONE, phoneNumber, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null);
    }

    public static IdentityPieceBc fromPersonalInfo(String firstName, String lastName, String idCardNumber, int documentType, Date documentValidTo, String contactZIP, String contactCountry, String contactCountryIso2, String contactProvince, String contactCity, String contactAddress, Date dateOfBirth, String occupation, String ssn) {
        return new IdentityPieceBc(TYPE_PERSONAL_INFORMATION, null, null, firstName, lastName, idCardNumber, documentType, documentValidTo, contactZIP, contactCountry, contactCountryIso2, contactProvince, contactCity, contactAddress, dateOfBirth, occupation, ssn, null, null, null,null);
    }

    public static IdentityPieceBc fromSsnAndOccupation(String ssn, String occupation, IIdentityPiece identityPiece){
        return new IdentityPieceBc(identityPiece.getPieceType(),
            identityPiece.getPhoneNumber(),
            identityPiece.getEmailAddress(),
            identityPiece.getFirstname(),
            identityPiece.getLastname(),
            identityPiece.getIdCardNumber(),
            identityPiece.getDocumentType(),
            identityPiece.getDocumentValidTo(),
            identityPiece.getContactZIP(),
            identityPiece.getContactCountry(),
            identityPiece.getContactCountryIso2(),
            identityPiece.getContactProvince(),
            identityPiece.getContactCity(),
            identityPiece.getContactAddress(),
            identityPiece.getDateOfBirth(),
            occupation,
            ssn,
            identityPiece.getFilename(),
            identityPiece.getMimeType(),
            identityPiece.getData(),
            identityPiece.getCreated());
    }

    public static IdentityPieceBc fromIIdentityPiece(IIdentityPiece identityPiece){
        return new IdentityPieceBc(identityPiece.getPieceType(),
            identityPiece.getPhoneNumber(),
            identityPiece.getEmailAddress(),
            identityPiece.getFirstname(),
            identityPiece.getLastname(),
            identityPiece.getIdCardNumber(),
            identityPiece.getDocumentType(),
            identityPiece.getDocumentValidTo(),
            identityPiece.getContactZIP(),
            identityPiece.getContactCountry(),
            identityPiece.getContactCountryIso2(),
            identityPiece.getContactProvince(),
            identityPiece.getContactCity(),
            identityPiece.getContactAddress(),
            identityPiece.getDateOfBirth(),
            identityPiece.getOccupation(),
            identityPiece.getSSN(),
            identityPiece.getFilename(),
            identityPiece.getMimeType(),
            identityPiece.getData(),
            identityPiece.getCreated());
    }

    @Override
    public int getPieceType() {
        return type;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public String getContactAddress() {
        return contactAddress;
    }

    @Override
    public String getContactCity() {
        return contactCity;
    }

    @Override
    public String getContactCountry() {
        return contactCountry;
    }

    @Override
    public String getContactCountryIso2() {
        return contactCountryIso2;
    }

    @Override
    public String getContactProvince() {
        return contactProvince;
    }

    @Override
    public String getContactZIP() {
        return contactZIP;
    }

    @Override
    public String getIssuingJurisdictionCountry() {
        return null;
    }

    @Override
    public String getIssuingJurisdictionProvince() {
        return null;
    }

    @Override
    public String getIdCardNumber() {
        return idCardNumber;
    }

    @Override
    public Integer getDocumentType() {
        return documentType;
    }

    @Override
    public Date getDocumentValidTo() {
        return documentValidTo;
    }

    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public IPerson getCreatedBy() {
        return null;
    }

    @Override
    public String getOccupation() {
        return occupation;
    }

    public String getSSN() {
        return ssn;
    }
}
