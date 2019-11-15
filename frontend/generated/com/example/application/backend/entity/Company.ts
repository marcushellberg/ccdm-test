import AbstractEntity from './AbstractEntity';
import Contact from './Contact';

export default interface Company extends AbstractEntity {
  employees: Array<Contact>;
  name: string;
}
