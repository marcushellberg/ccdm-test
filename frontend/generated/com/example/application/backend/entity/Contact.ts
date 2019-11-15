import AbstractEntity from './AbstractEntity';
import Company from './Company';
import ContactStatus from './ContactStatus';

export default interface Contact extends AbstractEntity {
  company: Company;
  email: string;
  firstName: string;
  lastName: string;
  status: ContactStatus;
}
