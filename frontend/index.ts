import {Flow} from '@vaadin/flow-frontend/Flow';
import {Router} from '@vaadin/router';

import './global-styles';
import './main-layout';

import './views/list/list-view';

const {serverSideRoutes} = new Flow({
  imports: () => import('../target/frontend/generated-flow-imports')
});

const routes = [
  {
    path: '',
    component: 'main-layout',
    children: [
      {path: '', component: 'list-view'},
      {path: 'list', component: 'list-view'},

      // add more client-side routes here
      // {
      //    path: '/another-view',
      //    component: 'another-view',
      //    action: async () => { await import('./views/another-view') }
      // },

      // fallback to server-side Flow routes if no client-side route matches
      ...serverSideRoutes
    ]
  }
];

const router = new Router(document.querySelector('#outlet'));
router.setRoutes(routes);
