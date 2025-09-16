import { contestReducer } from '../sub-stores/contest-store/contest.reducer';
import { ActionReducerMap } from '@ngrx/store';
import { AppState } from './app.state';

export const appReducers: ActionReducerMap<AppState> = {
  contest: contestReducer,
};
