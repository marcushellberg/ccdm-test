import { customElement, html, LitElement } from 'lit-element';
import '@vaadin/vaadin-text-field';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-button';

// @ts-ignore
import style from './list-view.css';
import Contact from '../../generated/com/example/application/backend/entity/Contact';
import { getContacts } from '../../generated/FrontendService';

@customElement('list-view')
export class ListViewElement extends LitElement {
  static styles = [style];
  contacts: Contact[] = [];

  render() {
    return html`
      <vaadin-grid .items=${this.contacts}> </vaadin-grid>
    `;
  }

  async firstUpdated() {
    this.contacts = await getContacts('');
  }
}
