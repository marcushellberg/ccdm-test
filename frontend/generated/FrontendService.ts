/**
 * FrontendService
 *
 * This module has been generated from FrontendService.java
 * @module FrontendService
 */

// @ts-ignore
import client from './connect-client.default';
import Contact from './com/example/application/backend/entity/Contact';

export function getContacts(
  stringFilter: string
): Promise<Array<Contact>> {
  return client.call('FrontendService', 'getContacts', {stringFilter}, {requireCredentials: false});
}
